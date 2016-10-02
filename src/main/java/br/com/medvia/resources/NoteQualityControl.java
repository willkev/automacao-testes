package br.com.medvia.resources;

import br.com.medvia.db.WkTable;

/**
 *
 * @author Willian Kirschner willkev@gmail.com.kirschner
 */
public class NoteQualityControl extends WkTable {

    private String description = "";
    private Integer userId;
    private Integer qualityControlId;
    // Date pattern "dd/MM/yyyy HH:mm"
    private String date = "";

    public Integer getQualityControlId() {
        return qualityControlId;
    }

    public void setQualityControlId(Integer qualityControlId) {
        this.qualityControlId = qualityControlId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "NoteQualityControl" + meToJson();
    }

}
