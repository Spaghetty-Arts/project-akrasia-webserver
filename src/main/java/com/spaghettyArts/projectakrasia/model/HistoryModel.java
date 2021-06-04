package com.spaghettyArts.projectakrasia.model;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * O modelo para o objeto HistoryModel que representa os elementos na tabela history da base dados.
 * O objeto possui como parametros: id (Integer), winer (Integer), loser(Integer), moment (timestamp)
 * A classe possui os construtores, getters e setters dos atributos tal como o hash code e equals
 * @author Fabian Nunes
 * @version 0.1
 */
@Entity
@Table(name = "history")
public class HistoryModel implements Serializable {
    private static final long serialVersionUID = 1728921855521678658L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "winner", nullable = false)
    private Integer winner;

    @Column(name = "loser", nullable = false)
    private Integer loser;

    @CreationTimestamp
    @Column(name = "moment")
    private Timestamp moment;

    public HistoryModel() {

    }

    public HistoryModel(Integer winner, Integer loser) {
        this.winner = winner;
        this.loser = loser;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWinner() {
        return winner;
    }

    public void setWinner(Integer winner) {
        this.winner = winner;
    }

    public Integer getLoser() {
        return loser;
    }

    public void setLoser(Integer loser) {
        this.loser = loser;
    }

    public Timestamp getMoment() {
        return moment;
    }

    public void setMoment(Timestamp moment) {
        this.moment = moment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HistoryModel that = (HistoryModel) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
