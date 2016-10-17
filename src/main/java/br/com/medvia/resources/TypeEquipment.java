package br.com.medvia.resources;

import br.com.medvia.db.WkTable;

/**
 *
 * @author Willian Kirschner willkev@gmail.com
 */
public class TypeEquipment extends WkTable {

    private String description;

    public TypeEquipment() {
    }
    
    public TypeEquipment(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String name) {
        this.description = name;
    }

    @Override
    public String toString() {
        return "TypeEquipment" + meToJson();
    }

}
