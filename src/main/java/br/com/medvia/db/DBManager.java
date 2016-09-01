package br.com.medvia.db;

import br.com.medvia.resources.Equipment;
import br.com.medvia.resources.Note;
import br.com.medvia.resources.Ticket;
import br.com.medvia.resources.User;
import java.io.File;

/**
 *
 * @author Willian
 */
public class DBManager {

//    private final String propJavaTmp = System.getProperty("java.io.tmpdir");
    private final String propUserHome = System.getProperty("user.home");

    private static final DBManager INSTANCE = new DBManager();

    public static DBManager getInstance() {
        return INSTANCE;
    }

    private final File fileDB;
    private final WkDB<Ticket> dbTicket;
    private final WkDB<Equipment> dbEquipment;
    private final WkDB<User> dbUser;
    private final WkDB<Note> dbNote;

    private DBManager() {
        fileDB = new File(propUserHome, "phymedvia.db");
        System.out.println("fileDB=" + fileDB.getAbsolutePath());

        WkDB.setFileDB(fileDB);

        dbTicket = new WkDB<>(Ticket.class);
        dbEquipment = new WkDB<>(Equipment.class);
        dbUser = new WkDB<>(User.class);
        dbNote = new WkDB<>(Note.class);

        // se o arqui ainda não existir
        if (!fileDB.exists() || fileDB.length() < 1) {
            dbTicket.createTable();
            dbEquipment.createTable();
            dbUser.createTable();
            dbNote.createTable();
        }
    }

    public File getFileDB() {
        return fileDB;
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

    public WkDB<Note> getDbNote() {
        return dbNote;
    }

    public synchronized void dropAndCreateTable() {
        dbTicket.dropAndCreateTable();
        dbEquipment.dropAndCreateTable();
        dbUser.dropAndCreateTable();
        dbNote.dropAndCreateTable();
        System.out.println("dropAndCreateTable!");
    }

}
