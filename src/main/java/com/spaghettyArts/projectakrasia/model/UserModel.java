package com.spaghettyArts.projectakrasia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

@Entity
@Table(name = "user")
public class UserModel implements Serializable {
    private static final long serialVersionUID = 1728921855521678658L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "username", nullable = false, length = 20, unique = true)
    private String username;

    @Column(name = "password", nullable = false, length = 60)
    private String password;

    @Column(name = "email", nullable = false, length = 60, unique = true)
    private String email;

    @Column(name = "money", nullable = false)
    private Integer money;

    @Column(name = "life", nullable = false)
    private Integer life;

    @Column(name = "last_login", nullable = false)
    private Date last_login;

    @Column(name = "login_reward", nullable = false)
    private Integer login_reward;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private ResetModel resetModel;


    /*
    Construtores
     */
    public UserModel() {

    }

    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public UserModel(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.money = 100;
        this.life = 100;
        this.login_reward = 0;
    }

    /*
        Getters e Setters
         */
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ResetModel getResetModel() {
        return resetModel;
    }

    public void setResetModel(ResetModel resetModel) {
        this.resetModel = resetModel;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public Integer getLife() {
        return life;
    }

    public void setLife(Integer life) {
        this.life = life;
    }

    public Date getLast_login() {
        return last_login;
    }

    public void setLast_login(Date last_login) {
        this.last_login = last_login;
    }

    public Integer getLogin_reward() {
        return login_reward;
    }

    public void setLogin_reward(Integer login_reward) {
        this.login_reward = login_reward;
    }

    /*
    Hash Code and equals
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserModel userModel = (UserModel) o;
        return id.equals(userModel.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
