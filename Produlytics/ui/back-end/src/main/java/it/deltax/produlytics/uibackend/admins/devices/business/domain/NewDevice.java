package it.deltax.produlytics.uibackend.admins.devices.business.domain;

//a device without the id
public record NewDevice(
	String name,
	boolean deactivated,
	boolean archived,
	String apiKey
) {}
