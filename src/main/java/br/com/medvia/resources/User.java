package br.com.medvia.resources;

import br.com.medvia.db.WkTable;

/**
 *
 * @author Willian Kirschner willkev@gmail.com
 */
public class User extends WkTable {

    private String email;
    private String password;
    private String name;
    // 0 - Super Admin (geral)
    // 1 - Admin (por Instituição)
    // 2 - Normal (pode ver e editar)
    // 3 - Visualizador (apenas pode ver)
    private Integer permissionLevel = 2;
    // Lista de IDs, separados por virgula
    private String institutionsList;

    public User() {
    }

    public String getInstitutionsList() {
        return institutionsList;
    }

    public void setInstitutionsList(String institutionsList) {
        this.institutionsList = institutionsList;
    }

    public Integer getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(Integer permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "User" + meToJson();
    }

    public void convertInstitutionsList(Integer[] institutions) {
        if (institutions != null && institutions.length > 0) {
            try {
                String IdList = "";
                for (Integer institutionId : institutions) {
                    if (institutionId != null) {
                        IdList += institutionId + ",";
                    }
                }
                this.institutionsList = IdList;
            } catch (Exception e) {
            }
        }
    }

}
