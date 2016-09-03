package br.com.medvia.resources;

import br.com.medvia.db.WkTable;

/**
 *
 * @author Willian
 */
public class Ticket extends WkTable {

    private String title = "";
    private String description = "";
    // '1a' : Aberto
    // '2f' : Fechado
    // '3e' : Excluído
    private String state = "a";

    // Datetime pattern "dd/MM/yyyy HH:mm"
    private String dateOcurrence = "";
    private String prediction = "";
    private String dateClosing = "";

    // Table User
    private int openedByID;
    // Table User
    private int responsableID;
    // Table Equipament
    private int equipmentID;
    // 0, 50, 75 or 100
    private String situation = "0";
    // '1a' : 'Alta'
    // '2n' : 'Normal'
    // '3b' : 'Baixa'
    private String priority = "b";
    private String noteClosing = "";

    public void setDateClosing(String dateClosing) {
        this.dateClosing = dateClosing;
    }

    public void setNoteClosing(String noteClosing) {
        this.noteClosing = noteClosing;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getState() {
        return state;
    }

    public String getDateOcurrence() {
        return dateOcurrence;
    }

    public String getPrediction() {
        return prediction;
    }

    public String getSituation() {
        return situation;
    }

    public String getPriority() {
        return priority;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateOcurrence(String dateOcurrence) {
        this.dateOcurrence = dateOcurrence;
    }

    public void setSituation(String situation) {
        this.situation = situation;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void setOpenedByID(int openedByID) {
        this.openedByID = openedByID;
    }

    public void setResponsableID(int responsableID) {
        this.responsableID = responsableID;
    }

    public void setEquipmentID(int equipmentID) {
        this.equipmentID = equipmentID;
    }

    public String getDateClosing() {
        return dateClosing;
    }

    public int getOpenedByID() {
        return openedByID;
    }

    public int getResponsableID() {
        return responsableID;
    }

    public int getEquipmentID() {
        return equipmentID;
    }

    public String getNoteClosing() {
        return noteClosing;
    }

    @Override
    public String toString() {
        return "Ticket" + meToJson();
    }

}
