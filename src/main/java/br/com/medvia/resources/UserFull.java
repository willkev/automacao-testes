package br.com.medvia.resources;

/**
 *
 * @author Willian Kirschner willkev@gmail.com
 */
public class UserFull extends User {

    private Integer[] institutions;

    public UserFull() {
    }

    public Integer[] getInstitutions() {
        return institutions;
    }

    public void setInstitutions(Integer[] institutions) {
        this.institutions = institutions;
    }

}
