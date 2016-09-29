package br.com.medvia.db;

import com.google.gson.Gson;

/**
 * @author Willian Kirschner willkev@gmail.com Kirschner
 * @version 2015 Jul, 15
 */
public class WkTable {

    //https://www.sqlite.org/datatype3.html
    //@WkDBType("text");
    //@WkDBType("varchar(33)");
    //@WkDBType("real");
    //@WkDBType("blob");    
    private int id = -1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    protected String meToJson() {
        return new Gson().toJson(this);
    }
}
