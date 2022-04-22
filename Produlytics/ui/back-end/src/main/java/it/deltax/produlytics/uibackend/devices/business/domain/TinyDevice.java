package it.deltax.produlytics.uibackend.devices.business.domain;

import lombok.Builder;

/**
 * Record che rappresenta l'intestazione di una macchina, con l'identificativo e il nome.
 */
public record TinyDevice(
        int id,
        String name
) {
	@Builder(toBuilder = true, builderMethodName = "", setterPrefix = "with")
	public TinyDevice {}
}
