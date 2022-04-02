package it.deltax.produlytics.uibackend.devices.business.domain;

import lombok.Builder;

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