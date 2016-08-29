package br.com.medvia.resources;

import br.com.medvia.db.WkTable;
import java.util.Date;

/**
 *
 * @author Willian
 */

/*
 {
 id: '',
 state: 'f',
 description: "Não liga",
 institution: "Hospital de Clínicas (POA)",
 equipment: "RM SPECTRIS SOLARIS EX THUNDER",
 situation: 0,
 priority: "n",
 dateOcurrence: "22/05/2016 23:08",
 prediction: "02/08/2016 10:45",
 openedBy: "Dr. Oliver Tsubasa",
 responsable: "Dr. Alberto A."
 };
 */
public class Ticket extends WkTable {

//    private int id;
    private String title;
    // Aberto, Fechado e Excluído
    private String state;
    private String description;
    private transient Date dateOcurrenceRaw;
    private String dateOcurrence;
    private transient Date predictionRaw;
    private String prediction;
    private String openedBy;
    private String responsable;
    private String institution;
    private String equipment;
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

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDateOcurrenceRaw(Date dateOcurrenceRaw) {
        this.dateOcurrenceRaw = dateOcurrenceRaw;
    }

    public Date getDateOcurrenceRaw() {
        return dateOcurrenceRaw;
    }

    public void setDateOcurrence(String dateOcurrence) {
        this.dateOcurrence = dateOcurrence;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public void setSituation(int situation) {
        this.situation = situation;
    }

    public void setPredictionRaw(Date predictionRaw) {
        this.predictionRaw = predictionRaw;
    }

    public Date getPredictionRaw() {
        return predictionRaw;
    }

    public void setPrediction(String prediction) {
        this.prediction = prediction;
    }

    public void setOpenedBy(String openedBy) {
        this.openedBy = openedBy;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String toString() {
        return "Ticket{" + "id=" + this.getID() + ", title=" + title + ", state=" + state + ", description=" + description + ", dateOcurrenceRaw=" + dateOcurrenceRaw + ", dateOcurrence=" + dateOcurrence + ", predictionRaw=" + predictionRaw + ", prediction=" + prediction + ", openedBy=" + openedBy + ", responsable=" + responsable + ", institution=" + institution + ", equipment=" + equipment + ", situation=" + situation + ", priority=" + priority + '}';
    }

}
