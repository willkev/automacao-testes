package br.com.medvia.resources;

import br.com.medvia.db.WkTable;

/**
 *
 * @author Willian
 */
public class Cost extends WkTable {

    private Double cost;
    private String description = "";
    private Integer userID;
    private Integer tickteID;
    // Date pattern "dd/MM/yyyy HH:mm"
    private String date = "";

    public Integer getTickteID() {
        return tickteID;
    }

    public void setTickteID(int tickteID) {
        this.tickteID = tickteID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Note" + meToJson();
    }

}
