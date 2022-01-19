package it.deltax.produlytics.persistence.rilevazioni;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class RilevazioneId implements Serializable {

	@Column(name = "creazione_utc", nullable = false)
	private long creazione_utc;

	@Column(name = "caratteristica", nullable = false)
	private String caratteristica;

	@Column(name = "macchina", nullable = false)
	private long macchina;

	protected RilevazioneId() {}

	public RilevazioneId(long creazione_utc, String caratteristica, long macchina) {
		this.creazione_utc = creazione_utc;
		this.caratteristica = caratteristica;
		this.macchina = macchina;
	}

	public long getCreazioneUtc() {
		return creazione_utc;
	}

	public String getCaratteristica() {
		return caratteristica;
	}

	public long getMacchina() {
		return macchina;
	}
}