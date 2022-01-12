package it.deltax.uibackend.db.rilevazioni.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "rilevazione")
public class Rilevazione {
    @Id
    @Column(name = "creazione_utc", nullable = false)
    private long id;

    @Column(name = "caratteristica", nullable = false)
    private String caratteristica;

    @Column(name = "macchina", nullable = false)
    private long macchina;

    @Column(name = "valore", nullable = false)
    private Double valore;

    @Column(name = "anomalo", nullable = false)
    private Boolean anomalo = false;

    public long getMacchina() {
        return macchina;
    }

    public long getId() {
        return id;
    }

    public Boolean getAnomalo() {
        return anomalo;
    }

    public void setAnomalo(Boolean anomalo) {
        this.anomalo = anomalo;
    }

    public Double getValore() {
        return valore;
    }

    public void setValore(Double valore) {
        this.valore = valore;
    }

    public String getCaratteristica() {
        return caratteristica;
    }

    public void setCaratteristica(String caratteristica) {
        this.caratteristica = caratteristica;
    }

}