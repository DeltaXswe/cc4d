package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import lombok.Builder;

/**
 * Il record rappresenta una macchina con tutti i suoi dati
 */
public record DetailedDevice(
	int id,
	String name,
	boolean deactivated,
	boolean archived,
	String apiKey
) {
	@Builder(toBuilder = true, builderMethodName = "", setterPrefix = "with")
	public DetailedDevice {}
}