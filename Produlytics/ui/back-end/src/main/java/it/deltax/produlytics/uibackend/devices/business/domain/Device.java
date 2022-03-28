package it.deltax.produlytics.uibackend.devices.business.domain;

public record Device(
	int id,
	String name,
	boolean deactivated,
	boolean archived,
	String apiKey
) {
}
