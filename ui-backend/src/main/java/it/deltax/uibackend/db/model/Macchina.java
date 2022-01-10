package it.deltax.uibackend.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Macchina {

    @Id()
    private String cod;

    private String name;

    protected Macchina() {}

    public Macchina(String cod, String name) {
        this.cod = cod;
        this.name = name;
    }

    public String getCod() {
        return cod;
    }

    public String getName() {
        return name;
    }
}
