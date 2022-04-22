package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import lombok.Builder;

import java.util.OptionalDouble;
import java.util.OptionalInt;

/**
 * Record che rappresenta i dati necessari per la creazione di una nuova caratteristica
 * Contiene tutti i campi di una caratteristica, meno il suo id, quello della macchina e lo stato di archiviazione
 * Mette a disposizione un builder con valori di default
 */
public record NewCharacteristic(
	String name,
	OptionalDouble upperLimit,
	OptionalDouble lowerLimit,
	boolean autoAdjust,
	OptionalInt sampleSize
) {
	@Builder(builderMethodName = "", setterPrefix = "with")
	public NewCharacteristic {}

	/**
	 * Fornisce il builder del record
	 * @return un nuovo builder con i seguenti valori di default:
	 *
	 * upperLimit: empty
	 * lowerLimit: empty
	 * sampleSize: empty
	 */
	public static NewCharacteristicBuilder builder() {
		return new NewCharacteristicBuilder()
			.withUpperLimit(OptionalDouble.empty())
			.withLowerLimit(OptionalDouble.empty())
			.withSampleSize(OptionalInt.empty());
	}
}
