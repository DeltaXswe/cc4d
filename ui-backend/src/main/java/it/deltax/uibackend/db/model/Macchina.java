package it.deltax.uibackend.db.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Macchina {

    @Id()
    private long ser;

    private String name;

    protected Macchina() {}

    public Macchina(long cod, String name) {
        this.ser = cod;
        this.name = name;
    }

    public long getSer() {
        return ser;
    }

    public String getName() {
        return name;
    }
}
