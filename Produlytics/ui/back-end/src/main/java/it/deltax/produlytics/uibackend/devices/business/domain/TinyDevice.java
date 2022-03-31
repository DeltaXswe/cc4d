package it.deltax.produlytics.uibackend.devices.business.domain;

import lombok.Builder;

public record TinyDevice(
        int id,
        String name
) {
	@Builder(toBuilder = true, builderMethodName = "", setterPrefix = "with")
	public TinyDevice {}
}
