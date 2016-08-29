package br.com.medvia.db;

/**
 * @author Willian Kirschner
 * @version 2015 Jul, 15
 */
public class WkTable {

    //https://www.sqlite.org/datatype3.html
    //@WkDBType("text");
    //@WkDBType("varchar(33)");
    //@WkDBType("real");
    //@WkDBType("blob");    
    private int ID;

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

}
