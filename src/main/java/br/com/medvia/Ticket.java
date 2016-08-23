package br.com.medvia;

import java.util.Date;

/**
 *
 * @author Willian
 */

/*
 "state": 0,
 "id": "",
 "description": "Baixa qualidade de imagem",
 "dateOcurrence": "31/05/2016 17:48",
 "responsable": "Marco Alexandre Reverdito Gomes",
 "institution": "Hospital Moinhos de Vento - Sede Ramiro",
 "equipment": "MEDRAD STELLANT DDX (INTERNADOS) - 20542",
 "situation": "75% funcional",
 "priority": "Baixa"
 */
public class Ticket {

    private int state;
    private int id;
    private String description;
    private transient Date dateOcurrenceRaw;
    private String dateOcurrence;
    private String responsable;
    private String institution;
    private String equipment;
    private int situation;

    public void setState(boolean state) {
        this.state = state ? 1 : 0;
    }

    public void setId(int id) {
        this.id = id;
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

}
