package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import lombok.Builder;

import java.util.OptionalDouble;
import java.util.OptionalInt;

/**
 * <p>Il record rappresenta una caratteristica con tutti i suoi dati
 * <p>Mette a disposizione un builder con valori di default
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
	@Builder(toBuilder = true, builderMethodName = "", setterPrefix = "with")
	public DetailedCharacteristic {}

	/**
	 * Fornisce il builder del record
	 * @return un nuovo builder con i seguenti valori di default:
	 * <ul>
	 * 	   <li>upperLimit: empty
	 * 	   <li>lowerLimit: empty
	 * 	   <li>sampleSize: empty
	 *     <li>archived: false
	 */
	public static DetailedCharacteristic.DetailedCharacteristicBuilder builder() {
		return new DetailedCharacteristicBuilder()
			.withUpperLimit(OptionalDouble.empty())
			.withLowerLimit(OptionalDouble.empty())
			.withSampleSize(OptionalInt.empty())
			.withArchived(false);
	}
}