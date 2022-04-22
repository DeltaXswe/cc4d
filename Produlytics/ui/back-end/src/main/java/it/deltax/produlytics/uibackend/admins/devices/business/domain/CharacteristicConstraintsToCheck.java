package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import lombok.Builder;

import java.util.OptionalDouble;
import java.util.OptionalInt;

/**
 * Record che rappresenta i dati necessari per effettuare i controlli di validità di una caratteristica
 * Contiene i campi opzionali di una caratteristica quello relativo all'autoAdjust, per poter effettuare i controlli
 * Mette a disposizione un builder con valori di default
 */
public record CharacteristicConstraintsToCheck(
	OptionalDouble upperLimit,
	OptionalDouble lowerLimit,
	boolean autoAdjust,
	OptionalInt sampleSize
) {
	@Builder(toBuilder = true, builderMethodName = "", setterPrefix = "with")
	public CharacteristicConstraintsToCheck {}

	/**
	 * Fornisce il builder del record
	 * @return un nuovo builder con i seguenti valori di default:
	 * upperLimit: empty
	 * lowerLimit: empty
	 * sampleSize: empty
	 */
	public static CharacteristicConstraintsToCheck.CharacteristicConstraintsToCheckBuilder builder() {
		return new CharacteristicConstraintsToCheckBuilder()
			.withUpperLimit(OptionalDouble.empty())
			.withLowerLimit(OptionalDouble.empty())
			.withSampleSize(OptionalInt.empty());
	}
}
