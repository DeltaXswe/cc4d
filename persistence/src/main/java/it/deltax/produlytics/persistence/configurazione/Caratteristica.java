package it.deltax.produlytics.persistence.configurazione;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "caratteristica", indexes = {
        @Index(name = "caratteristica_nome_key", columnList = "nome", unique = true)
})
public class Caratteristica {

    @EmbeddedId
    private CaratteristicaId id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "limite_min", nullable = false, precision = 131089)
    private BigDecimal limiteMin;

    @Column(name = "limite_max", nullable = false, precision = 131089)
    private BigDecimal limiteMax;

    @Column(name = "media", nullable = false, precision = 131089)
    private BigDecimal media;

    @Column(name = "adattamento", nullable = false)
    private Boolean adattamento = false;

    @Column(name = "ampiezza_campione", precision = 131089)
    private BigDecimal ampiezzaCampione;

    protected Caratteristica() {}

    public Caratteristica(CaratteristicaId id, String nome, BigDecimal limiteMin, BigDecimal limiteMax, BigDecimal media, Boolean adattamento, BigDecimal ampiezzaCampione) {
        this.id = id;
        this.nome = nome;
        this.limiteMin = limiteMin;
        this.limiteMax = limiteMax;
        this.media = media;
        this.adattamento = adattamento;
        this.ampiezzaCampione = ampiezzaCampione;
    }

    public void setId(CaratteristicaId id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setLimiteMin(BigDecimal limiteMin) {
        this.limiteMin = limiteMin;
    }

    public BigDecimal getAmpiezzaCampione() {
        return ampiezzaCampione;
    }

    public void setAmpiezzaCampione(BigDecimal ampiezzaCampione) {
        this.ampiezzaCampione = ampiezzaCampione;
    }

    public Boolean getAdattamento() {
        return adattamento;
    }

    public void setAdattamento(Boolean adattamento) {
        this.adattamento = adattamento;
    }

    public BigDecimal getMedia() {
        return media;
    }

    public void setMedia(BigDecimal media) {
        this.media = media;
    }

    public BigDecimal getLimiteMax() {
        return limiteMax;
    }

    public void setLimiteMax(BigDecimal limiteMax) {
        this.limiteMax = limiteMax;
    }

    public BigDecimal getLimiteMin() {
        return limiteMin;
    }

    public String getNome() {
        return nome;
    }

    public CaratteristicaId getId() {
        return id;
    }

}