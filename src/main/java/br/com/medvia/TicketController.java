package br.com.medvia;

import br.com.medvia.db.WkDB;
import br.com.medvia.mail.EmailSender;
import br.com.medvia.resources.Equipment;
import br.com.medvia.resources.Ticket;
import br.com.medvia.resources.User;
import br.com.medvia.util.Fakes;
import br.com.medvia.util.ReplyMessage;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Willian Kirschner willkev@gmail.com
 */
@RestController
@RequestMapping("/api/tickets")
@CrossOrigin
public class TicketController extends AbstractController {

    private static final Logger log = LoggerFactory.getLogger(TicketController.class);

    public static final String QUERY_LIST = "select uO.name openedBy, uR.name responsable, t.id id, t.state state, t.title title, i.description institution,(e.name || ' - ' || e.manufacturer) equipment, t.dateOcurrence dateOcurrence, t.prediction prediction, t.situation situation, t.priority priority from Ticket t, Equipment e, Institution i, (select * from User) uO, (select * from User) uR where t.userId = uO.id and t.responsableId = uR.id and t.equipmentId = e.id and e.institutionId = i.id";
    public static final String QUERY_LIST_ID = "select t.*,e.institutionId from Ticket t, Equipment e where t.equipmentId = e.id and t.id = ";

    private static final String PUT_CLOSE = "/{id}/close";
    private static final String PUT_DELETE = "/{id}/delete";

    private final WkDB<Ticket> db;
    private final WkDB<User> dbUser;
    private final WkDB<Equipment> dbEquipment;

    public TicketController() {
        super(TicketController.class.getSimpleName());
        db = new WkDB<>(Ticket.class);
        dbUser = new WkDB<>(User.class);
        dbEquipment = new WkDB<>(Equipment.class);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> list(@RequestHeader(value = "userId", required = false) String token) {
        Integer userId = verifyUser(token);
        User currentUser = dbUser.selectById(userId);
        if (currentUser == null) {
            // força exceção de permissão
            verifyUser(null);
        }
        // retorna resultados conforme o nível de permissão do User
        String filter = "";
        if (currentUser.getPermissionLevel() >= 1) {
            // a lista termina com uma virgula
            String institutionsList = currentUser.getInstitutionsList();
            filter = " and i.id in (" + institutionsList.substring(0, institutionsList.length() - 1) + ")";
        }
        List<Map<String, Object>> selectAll = db.executeQuery(QUERY_LIST + filter);
        return new ResponseEntity<>(selectAll, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ReplyMessage> create(@RequestHeader(value = "userId", required = false) String token,
            @RequestBody Ticket ticket) {
        // antes de setar, verifica User
        ticket.setUserId(verifyUser(token));
        // valida campos obrigatórios
        if (!isIntoRange(ticket.getResponsableId(), 1, Integer.MAX_VALUE)) {
            return returnFieldMandatory("Usuário Responsável");
        }
        if (!isNotNullNotEmpty(ticket.getDescription())) {
            return returnFieldMandatory("Descrição");
        }
        if (!isIntoRange(ticket.getEquipmentId(), 1, Integer.MAX_VALUE)) {
            return returnFieldMandatory("Equipamento");
        }
        ticket.setState("a");
        boolean insert = db.insert(ticket);
        String extraMsg = "";
        if (insert) {
            extraMsg = sendEmails(ticket, extraMsg);
        }
        return returnMsg(insert, "Criou novo chamado com sucesso!",
                "Não foi possível criar um novo chamdo!", extraMsg);
    }

    private String sendEmails(Ticket ticket, String extraMsg) {
        Equipment equipment = dbEquipment.selectById(ticket.getEquipmentId());
        User userCreator = dbUser.selectById(ticket.getUserId());
        User userResponsable = null;
        // Se o criador for diferente do responsável
        if (ticket.getUserId() != ticket.getResponsableId()) {
            userResponsable = dbUser.selectById(ticket.getResponsableId());
        }
        EmailSender emailSender = new EmailSender();
        boolean sentEmailOK = true;
        if (userCreator != null) {
            sentEmailOK = emailSender.sendCreateTicket(userCreator.getEmail(), userCreator, userResponsable, ticket, equipment);
        }
        if (userCreator == null || !sentEmailOK) {
            extraMsg += "Não foi possível enviar email para o usuário criador pelo chamado.";
        }
        sentEmailOK = true;
        if (userResponsable != null) {
            sentEmailOK = emailSender.sendCreateTicket(userResponsable.getEmail(), userCreator, userResponsable, ticket, equipment);
        }
        // Se o criador for diferente do responsável, mas não foi possível encontrar o responsável OU
        // não foi possível enviar o email ao responsável
        if ((ticket.getUserId() != ticket.getResponsableId() && userResponsable == null) || !sentEmailOK) {
            extraMsg += "Não foi possível enviar email para o usuário responsável pelo chamado.";
        }
        // envia emails para os emails adicionados no campo opcional
        if (ticket.getEmail() != null) {
            boolean sentOk = true;
            try {
                String ucEmail = userCreator.getEmail();
                String urEmail = userResponsable == null ? "" : userResponsable.getEmail();

                String[] emails = ticket.getEmail().split(",");
                for (String email : emails) {
                    email = email.trim();
                    // Se for diferente do criador e do responsável
                    if (!ucEmail.equals(email) && !urEmail.equals(email)) {
                        if (!emailSender.sendCreateTicket(email, userResponsable, userResponsable, ticket, equipment)) {
                            sentOk = false;
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Erro a enviar emails adicionais!", e);
            }
            // se houve algum erro ao enviar os emails adicionais
            if (!sentOk) {
                extraMsg += "Não foi possível enviar email para usuário(s) adicional(is).";
            }
        }
        return extraMsg;
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestHeader(value = "userId", required = false) String token,
            @PathVariable(value = "id") int id) {
        List<Map<String, Object>> selectOne = db.executeQuery(QUERY_LIST_ID + id);
        return new ResponseEntity<>(selectOne.get(0), HttpStatus.OK);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> edit(@RequestHeader(value = "userId", required = false) String token,
            @PathVariable(value = "id") int id, @RequestBody Ticket ticket) {
        // verifica User
        Integer userId = verifyUser(token);
        User currentUser = dbUser.selectById(userId);
        if (currentUser == null) {
            // força exceção de permissão
            verifyUser(null);
        }
        // Valida campos
        if (!isNotNullNotEmpty(ticket.getSituation())) {
            return returnFieldMandatory("Situação");
        }
        if (!isNotNullNotEmpty(ticket.getState())) {
            return returnFieldMandatory("Estado");
        }
        if (!isNotNullNotEmpty(ticket.getDateOcurrence())) {
            return returnFieldMandatory("Data de ocorrência");
        }
        if (!isNotNullNotEmpty(ticket.getPrediction())) {
            return returnFieldMandatory("Data de previsão de conserto");
        }
        if (!isIntoRange(ticket.getResponsableId(), 1, Integer.MAX_VALUE)) {
            return returnFieldMandatory("Responsável");
        }
        Ticket ticketOriginal = db.selectById(id);
        if (ticketOriginal == null) {
            return returnFail(ID_NOT_FOUND);
        }
        // altera apenas os dados que podem ser alterados
        ticketOriginal.setSituation(ticket.getSituation());
        ticketOriginal.setState(ticket.getState());
        ticketOriginal.setDateOcurrence(ticket.getDateOcurrence());
        ticketOriginal.setPrediction(ticket.getPrediction());
        ticketOriginal.setResponsableId(ticket.getResponsableId());
        boolean update = db.update(ticket);

        EmailSender emailSender = new EmailSender();
        String extraMsg = "";
        if (!update) {
            extraMsg = "Não foi possível enviar email para o usuário editor do chamado.";
        } else {
            User userCreator = dbUser.selectById(ticket.getUserId());
            User userResponsable = dbUser.selectById(ticket.getResponsableId());
            Equipment equipment = dbEquipment.selectById(ticket.getEquipmentId());

            emailSender.sendEditTicket(currentUser.getEmail(), userCreator, userResponsable, ticket, equipment);
        }
        return returnMsgUpdate(update, extraMsg);
    }

    @RequestMapping(path = PUT_CLOSE, method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> close(@RequestHeader(value = "userId", required = false) String token,
            @PathVariable(value = "id") int id, @RequestBody Ticket ticket) {
        // antes de setar, verifica User
        Integer userId = verifyUser(token);
        User currentUser = dbUser.selectById(userId);
        if (currentUser == null) {
            // força exceção de permissão
            verifyUser(null);
        }
        if (!isNotNullNotEmpty(ticket.getDateClosing())) {
            return returnFieldMandatory("Data de Fechamento");
        }
        if (!isNotNullNotEmpty(ticket.getNoteClosing())) {
            return returnFieldMandatory("Nota de Fechamento");
        }
        Ticket ticketOriginal = db.selectById(id);
        if (ticketOriginal == null) {
            return returnFail(ID_NOT_FOUND);
        }
        // altera apenas os dados do fechamento
        ticketOriginal.setDateClosing(ticket.getDateClosing());
        ticketOriginal.setNoteClosing(ticket.getNoteClosing());
        ticketOriginal.setState("f");
        boolean update = db.update(ticketOriginal);

        EmailSender emailSender = new EmailSender();
        String extraMsg = "";
        if (!update) {
            extraMsg = "Não foi possível enviar email para o usuário fechador do chamado.";
        } else {
            User userCreator = dbUser.selectById(ticket.getUserId());
            User userResponsable = dbUser.selectById(ticket.getResponsableId());
            Equipment equipment = dbEquipment.selectById(ticket.getEquipmentId());

            emailSender.sendCloseTicket(currentUser.getEmail(), userCreator, userResponsable, ticket, equipment);
        }
        return returnMsg(update, "Close OK!", "Close Fail!", extraMsg);
    }

    @RequestMapping(path = PUT_DELETE, method = RequestMethod.PUT)
    public ResponseEntity<ReplyMessage> delete(@RequestHeader(value = "userId", required = false) String token,
            @PathVariable(value = "id") int id, @RequestBody Ticket ticket) {
        if (!isNotNullNotEmpty(ticket.getDateRemoving())) {
            return returnFieldMandatory("Data de Exclusão");
        }
        if (!isNotNullNotEmpty(ticket.getNoteRemoving())) {
            return returnFieldMandatory("Nota de Exclusão");
        }
        Ticket ticketOriginal = db.selectById(id);
        if (ticketOriginal == null) {
            return returnFail(ID_NOT_FOUND);
        }
        // altera apenas os dados da deleção
        ticketOriginal.setDateRemoving(ticket.getDateRemoving());
        ticketOriginal.setNoteRemoving(ticket.getNoteRemoving());
        ticketOriginal.setState("e");
        boolean delete = db.update(ticketOriginal);
        return returnMsgDelete(delete);
    }

    @RequestMapping(PATH_FAKES)
    public ResponseEntity<ReplyMessage> createFakes() {
        List<User> users = new WkDB<>(User.class).selectAll();
        // se ainda não existir nenhum 
        if (users.isEmpty()) {
            return returnFail("Nenhum usuário ainda foi criado!");
        }
        List<Equipment> equipments = new WkDB<>(Equipment.class).selectAll();
        // se ainda não existir nenhum 
        if (equipments.isEmpty()) {
            return returnFail("Nenhum equipamento ainda foi criado!");
        }
        List<Ticket> created = Fakes.createTickets(users, equipments);
        created.stream().forEach((element) -> {
            create("" + element.getUserId(), element);
        });
        return fakesCreated(created.size());
    }

}
