package com.spaghettyArts.projectakrasia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Objects;

/**
 * O modelo para o objeto UserModel que representa os elementos na tabela user da base dados.
 * O objeto possui como parametros: id (Integer id do user), username (string username do user),
 * password(string password do user) ,email (string email do user), money (Integer dinheiro do user),
 * armor_level(Integer armadura do user) , last_login (Date data do último login no jogo),
 * login_reward (Integer número de dias consecutivos de login), got_reward(Integer representa se o user já recebeu
 * o daily reward), user_online (Integer representa se o user está ativo no jogo), token (string token para validar certos requests),
 * winP (Integer número de vitórias), loseP (Integer númer de derrotas), rankP (Integer rank do user)
 * last_action (timestamp hora da última ação realizada pelo user),  resetModel (ResetModel necessário para fazer a ligação das duas tabelas pelo spring)
 * A classe possui os construtores, getters e setters dos atributos tal como o hash code e equals
 * @author Fabian Nunes
 * @version 0.1
 */
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

    @Column(name = "armor_level", nullable = false)
    private Integer life;

    @Column(name = "last_login", nullable = false)
    private Date last_login;

    @Column(name = "login_reward", nullable = false)
    private Integer login_reward;

    @Column(name = "got_reward", nullable = false)
    private Integer got_reward;

    @Column(name = "active", nullable = false)
    private Integer user_online;

    @Column(name = "token")
    private String user_token;

    @Column(name = "winP", nullable = false)
    private Integer win;

    @Column(name = "loseP", nullable = false)
    private Integer lose;

    @Column(name = "rankP", nullable = false)
    private Integer rank;

    @Column(name = "last_action", nullable = false)
    private Timestamp last_action;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private ResetModel resetModel;


    /*
    Construtores
     */
    public UserModel() {

    }

    /**
     * O construtor do objeto UserModel que é utilizado no registo, serão criados vários parametros default tal como:
     * dinheiro a 0, armadura a nível 1, ainda não recebeu reward diario (0), o número de logins consecutivos a 0,
     * o user não está online (0), 0 vitorias e derrotas, rank 1 e a última ação foi no momento atual.
     * @param username username do utilizador
     * @param password password do utilizador
     * @param email email do utilizador
     * @author Fabian Nunes
     */
    public UserModel(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.money = 0;
        this.life = 1;
        this.got_reward = 0;
        this.login_reward = 0;
        this.user_online = 0;
        this.win = 0;
        this.lose = 0;
        this.rank = 1;
        last_action = Timestamp.from(ZonedDateTime.now().toInstant());
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

    public Integer getGot_reward() {
        return got_reward;
    }

    public void setGot_reward(Integer got_reward) {
        this.got_reward = got_reward;
    }

    public Integer getUser_online() {
        return user_online;
    }

    public void setUser_online(Integer user_online) {
        this.user_online = user_online;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public Integer getWin() {
        return win;
    }

    public void setWin(Integer win) {
        this.win = win;
    }

    public Integer getLose() {
        return lose;
    }

    public void setLose(Integer lose) {
        this.lose = lose;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public Timestamp getLast_action() {
        return last_action;
    }

    public void setLast_action(Timestamp last_action) {
        this.last_action = last_action;
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
