package br.com.medvia.resources;

import br.com.medvia.db.WkTable;

/**
 *
 * @author Willian Kirschner willkev@gmail.com
 */
public class QualityControl extends WkTable {

    /*
     OBS: alguns campos precisam de valor default para garantir a validação automática do rest-bind do JSON para o Objetct
     */
    // description/tilte
    private String test;
    Integer equipmentId;
    // Date pattern "dd/MM/yyyy HH:mm"
    private String dateExecution;

    public String getTest() {
        return test;
    }

    public void setTest(String test) {
        this.test = test;
    }

    public Integer getEquipmentId() {
        return equipmentId;
    }

    public void setEquipmentId(Integer equipmentId) {
        this.equipmentId = equipmentId;
    }

    public String getDateExecution() {
        return dateExecution;
    }

    public void setDateExecution(String dateExecution) {
        this.dateExecution = dateExecution;
    }

    public String getDateValidity() {
        return dateValidity;
    }

    public void setDateValidity(String dateValidity) {
        this.dateValidity = dateValidity;
    }

    public Boolean getCompliance() {
        return compliance;
    }

    public void setCompliance(Boolean compliance) {
        this.compliance = compliance;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    private String dateValidity;
    private Boolean compliance;
    private Integer userId;

    @Override
    public String toString() {
        return "QualityControl" + meToJson();
    }

}
