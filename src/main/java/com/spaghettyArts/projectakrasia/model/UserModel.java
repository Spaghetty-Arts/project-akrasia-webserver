package com.spaghettyArts.projectakrasia.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "user")
public class UserModel {

    @Id
    @GeneratedValue
    public Integer id;

    @Column(nullable = false, length = 20, unique = true)
    public String username;

    @Column(nullable = false, length = 20)
    public String password;


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
}
