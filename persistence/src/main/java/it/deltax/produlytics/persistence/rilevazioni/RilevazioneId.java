package it.deltax.produlytics.persistence.rilevazioni;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RilevazioneId implements Serializable {

	@Column(name = "creazione_utc", nullable = false)
	private long creazioneUtc;

	@Column(name = "caratteristica", nullable = false)
	private String caratteristica;

	@Column(name = "macchina", nullable = false)
	private long macchina;

	protected RilevazioneId() {}

	public RilevazioneId(long creazioneUtc, String caratteristica, long macchina) {
		this.creazioneUtc = creazioneUtc;
		this.caratteristica = caratteristica;
		this.macchina = macchina;
	}

	public long getCreazioneUtc() {
		return creazioneUtc;
	}

	public String getCaratteristica() {
		return caratteristica;
	}

	public long getMacchina() {
		return macchina;
	}
}