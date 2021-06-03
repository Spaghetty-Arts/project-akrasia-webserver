package com.spaghettyArts.projectakrasia.model;

/**
 * O modelo para o objeto ResetFormModel que é obtido apartir dos dados do form HTML.
 * O objeto possui como parametros duas strings email e password.
 * A classe possui getters e setters dos atributos.
 * @author Fabian Nunes
 * @version 0.1
 */
public class ResetFormModel {

    private String email;
    private String password;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
