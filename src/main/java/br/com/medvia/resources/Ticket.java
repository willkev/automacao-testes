package br.com.medvia.resources;

import br.com.medvia.db.WkTable;

/**
 *
 * @author Willian
 */
public class Ticket extends WkTable {

    private String title;
    private String description;
    // 'a' : Aberto
    // 'f' : Fechado
    // 'e' : Excluído
    private String state;
    // Date "dd/MM/yyyy HH:mm"
    private String dateOcurrence;
    // Date "dd/MM/yyyy HH:mm"
    private String prediction;
    // Table User
    private int openedByID;
    // Table User
    private int responsableID;
    // Table Equipament
    private int equipmentID;
    // 0, 50, 75 or 100
    private int situation;
    // 'a' : 'Alta'
    // 'n' : 'Normal'
    // 'b' : 'Baixa'
    private String priority;

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

    public int getSituation() {
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

    public void setSituation(int situation) {
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

}
