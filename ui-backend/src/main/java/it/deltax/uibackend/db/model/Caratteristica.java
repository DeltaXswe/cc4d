package it.deltax.uibackend.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Caratteristica {

    @Id()
    private String nome;

    private long macchina;

    protected Caratteristica() {}

    public Caratteristica(String nome, long macchina) {
        this.nome = nome;
        this.macchina = macchina;
    }

    public String getNome() {
        return nome;
    }

    public long getMacchina() {
        return macchina;
    }
}
