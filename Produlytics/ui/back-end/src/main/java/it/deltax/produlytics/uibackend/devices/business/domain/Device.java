package it.deltax.produlytics.uibackend.devices.business.domain;

import lombok.Builder;

public record Device(
	int id,
	String name,
	boolean deactivated,
	boolean archived
) {
	@Builder(toBuilder = true, builderMethodName = "", setterPrefix = "with")
	public Device{}
}
