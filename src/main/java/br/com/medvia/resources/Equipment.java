package br.com.medvia.resources;

import br.com.medvia.db.WkTable;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

/**
 *
 * @author willian.kirschner
 */
@JsonInclude(Include.NON_NULL)
public class Equipment extends WkTable {

    private String name;
    private int institutionId;
    private String brand;
    private String model;
    private String manufacturer;
    // Date pattern "dd/MM/yyyy"
    private String manufactureDate;
    private String installationDate;
    
    private String registryMS;
    private String serieNumber;
    private int typeEquipmentId;
    private String observation;
    private boolean active;
    private int workedHoursPerDay;
    private int daysPerWeek;
    private int userId;
    
    // Se for necessário em algum momento garantir que os campos não setados
    // fiquem com um valor padrão
    public void initFields() {
        // setar padrão de todos os campos SE não tiverem
        //
        // deve haver um if para cada campo verificando se ele ainda não foi setado
        //
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getManufactureDate() {
        return manufactureDate;
    }

    public void setManufactureDate(String manufactureDate) {
        this.manufactureDate = manufactureDate;
    }

    public String getInstallationDate() {
        return installationDate;
    }

    public void setInstallationDate(String installationDate) {
        this.installationDate = installationDate;
    }

    public String getSerieNumber() {
        return serieNumber;
    }

    public void setSerieNumber(String serieNumber) {
        this.serieNumber = serieNumber;
    }

    public int getTypeEquipmentId() {
        return typeEquipmentId;
    }

    public void setTypeEquipmentId(int typeEquipmentId) {
        this.typeEquipmentId = typeEquipmentId;
    }

    public int getWorkedHoursPerDay() {
        return workedHoursPerDay;
    }

    public void setWorkedHoursPerDay(int workedHoursPerDay) {
        this.workedHoursPerDay = workedHoursPerDay;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void setInstitutionId(int institutionId) {
        this.institutionId = institutionId;
    }

    public int getInstitutionId() {
        return institutionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegistryMS() {
        return registryMS;
    }

    public void setRegistryMS(String registryMS) {
        this.registryMS = registryMS;
    }

    public int getDaysPerWeek() {
        return daysPerWeek;
    }

    public void setDaysPerWeek(int daysPerWeek) {
        this.daysPerWeek = daysPerWeek;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Equipment" + meToJson();
    }

}
