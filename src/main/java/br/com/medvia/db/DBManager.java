package br.com.medvia.db;

import br.com.medvia.resources.Equipment;
import br.com.medvia.resources.Ticket;
import br.com.medvia.resources.User;
import java.io.File;

/**
 *
 * @author Willian
 */
public class DBManager {

    private final String propJavaTmp = System.getProperty("java.io.tmpdir");
    private final String propUserHome = System.getProperty("user.home");

    private static final DBManager INSTANCE = new DBManager();

    public static DBManager getInstance() {
        return INSTANCE;
    }

    private final WkDB<Ticket> dbTicket;
    private final WkDB<Equipment> dbEquipment;
    private final WkDB<User> dbUser;

    private DBManager() {
        // busca pelo arquivo do banco de dados, Se não existir ainda deve criar
        File fileDB = new File(propUserHome, "phymedvia.db");
        System.out.println("fileDB=" + fileDB.getAbsolutePath());
        WkDB.setFileDB(fileDB);
        dbTicket = new WkDB<>(Ticket.class);
        dbEquipment = new WkDB<>(Equipment.class);
        dbUser = new WkDB<>(User.class);
        if (!fileDB.exists() || fileDB.length() < 0) {
            boolean createTable0 = dbTicket.createTable();
            boolean createTable1 = dbEquipment.createTable();
            boolean createTable2 = dbUser.createTable();
        }
    }

    public WkDB<Ticket> getDbTicket() {
        return dbTicket;
    }

    public WkDB<Equipment> getDbEquipment() {
        return dbEquipment;
    }

    public WkDB<User> getDbUser() {
        return dbUser;
    }

    public void dropAndCreateTable() {
        boolean dropAndCreateTable0 = dbTicket.dropAndCreateTable();
        boolean dropAndCreateTable1 = dbEquipment.dropAndCreateTable();
        boolean dropAndCreateTable2 = dbUser.dropAndCreateTable();

    }

}
