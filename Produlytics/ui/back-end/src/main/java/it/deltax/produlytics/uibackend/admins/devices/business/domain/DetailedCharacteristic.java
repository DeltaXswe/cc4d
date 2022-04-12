package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import lombok.Builder;

import java.util.OptionalDouble;
import java.util.OptionalInt;

/**
 * <p>Il record rappresenta una caratteristica con tutti i suoi dati
 * <p>Mette a disposizione un builder
 */
public record DetailedCharacteristic(
	int id,
	int deviceId,
	String name,
	OptionalDouble lowerLimit,
	OptionalDouble upperLimit,
	boolean autoAdjust,
	OptionalInt sampleSize,
	boolean archived
) {
	@Builder(toBuilder = true, setterPrefix = "with")
	public DetailedCharacteristic {}
}
