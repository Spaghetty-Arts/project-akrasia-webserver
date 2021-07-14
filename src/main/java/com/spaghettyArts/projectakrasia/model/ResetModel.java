package com.spaghettyArts.projectakrasia.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * O modelo para o objeto ResetModel que representa os elementos na tabela reset da base dados.
 * O objeto possui como parametros: id (Integer), email (string), token (string), request_date (timestamp), user (Usermodel foreign key que será o id do user).
 * A classe possui os construtores, getters e setters dos atributos tal como o hash code e equals
 * @author Fabian Nunes
 * @version 1.0
 */
@Entity
@Table(name = "reset")
public class ResetModel implements Serializable {
    private static final long serialVersionUID = 1728921855521678658L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "email", nullable = false, length = 60, unique = true)
    private String email;

    @Column(name = "token", nullable = false, length = 60, unique = true)
    private String token;

    @CreationTimestamp
    @Column(name = "request_date")
    private Timestamp request_date;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserModel user;

    public ResetModel() {

    }

    public ResetModel(String email, String token) {
        this.email = email;
        this.token = token;
    }

    public ResetModel( String token, UserModel user) {
        this.email = user.getEmail();
        this.token = token;
        this.user = user;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel userModel) {
        this.user = userModel;
    }

    public Timestamp getRequest_date() {
        return request_date;
    }

    public void setRequest_date(Timestamp request_date) {
        this.request_date = request_date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResetModel that = (ResetModel) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
