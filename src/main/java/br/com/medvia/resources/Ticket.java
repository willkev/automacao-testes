package br.com.medvia.resources;

import br.com.medvia.db.WkTable;

/**
 *
 * @author Willian
 */
public class Ticket extends WkTable {

    private String title = "";
    private String description = "";
    // 'a' : Aberto
    // 'f' : Fechado
    // 'e' : Excluído
    private String state = "a";

    // Datetime pattern "dd/MM/yyyy HH:mm"
    private String dateOcurrence = "";
    private String prediction = "";
    private String dateClosing = "";
    private String dateRemoving = "";

    // Table User
    private int userId;
    private int responsableId;
    
    // Table Equipament
    private int equipmentId;
    
    // 0(parado), 50, 75 or 100
    private String situation = "0";
    // 'a' : 'Alta'
    // 'n' : 'Normal'
    // 'b' : 'Baixa'
    private String priority = "b";
    private String noteClosing = "";
    private String noteRemoving = "";

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

    public void setResponsableId(int responsableId) {
        this.responsableId = responsableId;
    }

    public void setEquipmentId(int equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getDateClosing() {
        return dateClosing;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getResponsableId() {
        return responsableId;
    }

    public int getEquipmentId() {
        return equipmentId;
    }

    public String getNoteClosing() {
        return noteClosing;
    }

    public String getDateRemoving() {
        return dateRemoving;
    }

    public void setDateRemoving(String dateRemoving) {
        this.dateRemoving = dateRemoving;
    }

    public String getNoteRemoving() {
        return noteRemoving;
    }

    public void setNoteRemoving(String noteRemoving) {
        this.noteRemoving = noteRemoving;
    }

    @Override
    public String toString() {
        return "Ticket" + meToJson();
    }

}
