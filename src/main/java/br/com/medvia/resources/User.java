package br.com.medvia.resources;

import br.com.medvia.db.WkTable;

/**
 *
 * @author willian.kirschner
 */
public class User extends WkTable {

    private String name = "";

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "User" + meToJson();
    }

}
