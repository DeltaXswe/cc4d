package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import lombok.Builder;

/**
 * Il record rappresenta una macchina con tutti i suoi dati, meno la apiKey
 * @author Leila Dardouri
 */
public record Device(
	int id,
	String name,
	boolean deactivated,
	boolean archived
) {
	@Builder(toBuilder = true, builderMethodName = "", setterPrefix = "with")
	public Device{}
}
