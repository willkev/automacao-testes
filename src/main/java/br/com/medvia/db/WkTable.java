package br.com.medvia.db;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.Gson;

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
    @JsonProperty("Id")
    private int Id = -1;

    @JsonProperty("Id")
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    protected String meToJson() {
        return new Gson().toJson(this);
    }
}
