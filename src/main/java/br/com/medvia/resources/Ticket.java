package br.com.medvia.resources;

import br.com.medvia.db.WkTable;

/**
 *
 * @author Willian
 */

/*
 {
 id: '',
 state: 'f',
 description: "Não liga",
 institution: "Hospital de Clínicas (POA)",
 equipment: "RM SPECTRIS SOLARIS EX THUNDER",
 situation: 0,
 priority: "n",
 dateOcurrence: "22/05/2016 23:08",
 prediction: "02/08/2016 10:45",
 openedBy: "Dr. Oliver Tsubasa",
 responsable: "Dr. Alberto A."
 };
 */
public class Ticket extends WkTable {

    private String title;
    private String description;
    // 'a' : Aberto
    // 'f' : Fechado
    // 'e' : Excluído
    private String state;
    // Date "dd/MM/yyyy HH:mm"
    private String dateOcurrence;
    // Date "dd/MM/yyyy HH:mm"
    private String prediction;
    // Table User
    private int openedBy;
    // Table User
    private int responsable;
    // Table Institution
    private int institution;
    // Table Equipament
    private int equipment;
    // 0, 50, 75 or 100
    private int situation;
    // 'a' : 'Alta'
    // 'n' : 'Normal'
    // 'b' : 'Baixa'
    private String priority;

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOpenedBy(int openedBy) {
        this.openedBy = openedBy;
    }

    public void setResponsable(int responsable) {
        this.responsable = responsable;
    }

    public void setInstitution(int institution) {
        this.institution = institution;
    }

    public void setEquipment(int equipment) {
        this.equipment = equipment;
    }

    public void setDateOcurrence(String dateOcurrence) {
        this.dateOcurrence = dateOcurrence;
    }

    public void setSituation(int situation) {
        this.situation = situation;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Ticket{" + "id=" + this.getID() + "title=" + title + ", state=" + state + ", description=" + description + ", dateOcurrence=" + dateOcurrence + ", prediction=" + prediction + ", openedBy=" + openedBy + ", responsable=" + responsable + ", institution=" + institution + ", equipment=" + equipment + ", situation=" + situation + ", priority=" + priority + '}';
    }

}
