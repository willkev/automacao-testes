package br.com.medvia.resources;

import br.com.medvia.db.WkTable;

/**
 *
 * @author willian.kirschner
 */
public class Equipment extends WkTable {

    private String description = "";
    private int institutionID;

    public void setInstitutionID(int institutionID) {
        this.institutionID = institutionID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Equipment" + meToJson();
    }

}
