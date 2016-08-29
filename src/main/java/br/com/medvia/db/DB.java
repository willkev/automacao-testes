package br.com.medvia.db;

import java.io.File;

/**
 *
 * @author Willian
 */
public class DB {

    private final String propJavaTmp = System.getProperty("java.io.tmpdir");
    private final String propUserHome = System.getProperty("user.home");
    
    private static final DB INSTANCE = new DB();

    private DB() {
        // busca pelo arquivo do banco de dados, Se não existir ainda deve criar
        File fileDB = new File(propUserHome);
        if (fileDB != null && fileDB.exists() && fileDB.isFile() && fileDB.length() > 0) {
            
        }
    }
}
