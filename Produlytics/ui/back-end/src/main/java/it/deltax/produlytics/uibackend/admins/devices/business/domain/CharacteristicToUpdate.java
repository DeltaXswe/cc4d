package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import lombok.Builder;

import java.util.OptionalDouble;
import java.util.OptionalInt;

/**
 * Record che rappresenta i dati necessari per la modifica di una caratteristica.
 * Contiene tutti i campi di una caratteristica, meno lo stato di archiviazione.
 * Mette a disposizione un builder con valori di default
 */
public record CharacteristicToUpdate(
	int id,
	int deviceId,
	String name,
	OptionalDouble upperLimit,
	OptionalDouble lowerLimit,
	boolean autoAdjust,
	OptionalInt sampleSize
) {
	@Builder(toBuilder = true, builderMethodName = "", setterPrefix = "with")
	public CharacteristicToUpdate {}

	/**
	 * Fornisce il builder del record
	 * @return un nuovo builder con i seguenti valori di default:
	 *
	 * upperLimit: empty
	 * lowerLimit: empty
	 * sampleSize: empty
	 */
	public static CharacteristicToUpdateBuilder builder() {
		return new CharacteristicToUpdateBuilder()
			.withUpperLimit(OptionalDouble.empty())
			.withLowerLimit(OptionalDouble.empty())
			.withSampleSize(OptionalInt.empty());
	}
}
