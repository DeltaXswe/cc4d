package it.deltax.produlytics.uibackend.admins.devices.business.domain;

/**
 * Il record rappresenta una macchina con tutti i suoi dati, meno l'id
 */
public record NewDevice(
	String name,
	boolean deactivated,
	boolean archived,
	String apiKey
) {}
