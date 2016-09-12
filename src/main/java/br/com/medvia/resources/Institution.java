package br.com.medvia.resources;

import br.com.medvia.db.WkTable;

/**
 *
 * @author willian.kirschner
 */
public class Institution extends WkTable {

    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Institution" + meToJson();
    }

}
