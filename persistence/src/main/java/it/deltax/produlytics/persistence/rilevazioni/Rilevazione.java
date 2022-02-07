package it.deltax.produlytics.persistence.rilevazioni;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "rilevazione")
public class Rilevazione {
	@EmbeddedId
	private RilevazioneId id;

	@Column(name = "valore", nullable = false)
	private Double valore;

	@Column(name = "anomalo", nullable = false)
	private Boolean anomalo = false;

	protected Rilevazione() {}

	public Rilevazione(RilevazioneId id, Double valore, Boolean anomalo) {
		this.id = id;
		this.valore = valore;
		this.anomalo = anomalo;
	}

	public RilevazioneId getID() {
		return id;
	}
	public void setId(RilevazioneId id) {
		this.id = id;
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
}