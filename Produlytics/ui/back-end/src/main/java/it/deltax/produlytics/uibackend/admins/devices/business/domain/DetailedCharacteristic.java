package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import lombok.Builder;

/**
 * Il record rappresenta una caratteristica con tutti i suoi dati
 */
public record DetailedCharacteristic(
	int id,
	int deviceId,
	String name,
	double lowerLimit,
	double upperLimit,
	boolean autoAdjust,
	int sampleSize,
	boolean archived
) {
	@Builder(toBuilder = true, setterPrefix = "with")
	public DetailedCharacteristic {}
}
