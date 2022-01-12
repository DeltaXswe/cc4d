package it.deltax.uibackend.db.configurazione.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.io.Serializable;

@Embeddable
public class CaratteristicaId implements Serializable {

    @Column(name = "macchina", nullable = false)
    private long macchina;

    @Column(name = "codice", nullable = false)
    private String codice;

    protected CaratteristicaId() {}

    public CaratteristicaId(long macchina, String codice) {
        this.macchina = macchina;
        this.codice = codice;
    }

    public long getMacchina() {
        return macchina;
    }

    public String getCodice() {
        return codice;
    }
}