package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import lombok.Builder;

import java.util.OptionalDouble;
import java.util.OptionalInt;

/**
 * <p>Record che rappresenta i dati necessari per la creazione di una nuova caratteristica
 * <p>Contiene tutti i campi di una caratteristica, meno il suo id, quello della macchina e lo stato di archiviazione
 * <p>Mette a disposizione un builder con valori di defaiult
 */
public record NewCharacteristic(
	String name,
	OptionalDouble upperLimit,
	OptionalDouble lowerLimit,
	boolean autoAdjust,
	OptionalInt sampleSize
) {
	@Builder(toBuilder = true, builderMethodName = "", setterPrefix = "with")
	public NewCharacteristic {}

	/**
	 * Fornisce il builder del record
	 * @return un nuovo builder con i seguenti valori di default:
	 * <ul>
	 *     <li>upperLimit: empty
	 * 	   <li>lowerLimit: empty
	 * 	   <li>sampleSize: empty
	 */
	public static NewCharacteristicBuilder builder() {
		return new NewCharacteristicBuilder()
			.withUpperLimit(OptionalDouble.empty())
			.withLowerLimit(OptionalDouble.empty())
			.withSampleSize(OptionalInt.empty());
	}
}
