package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import lombok.Builder;

import java.util.OptionalDouble;
import java.util.OptionalInt;

/**
 * <p>Record che rappresenta i dati necessari per la creazione di una nuova caratteristica
 * <p>Contiene tutti i campi di una caratteristica, meno il suo id e quello della macchina
 * <p>Mette a disposizione un builder
 */
public record NewCharacteristic(
	String name,
	OptionalDouble upperLimit,
	OptionalDouble lowerLimit,
	boolean autoAdjust,
	OptionalInt sampleSize,
	boolean archived
) {
	@Builder(toBuilder = true, builderMethodName = "", setterPrefix = "with")
	public NewCharacteristic {}

	/**
	 * Fornisce il builder del record
	 * @return un nuovo builder con i seguenti valori di default:
	 * <ul>
	 * 	<li>archived: false
	 * 	<li>upperLimit: empty
	 * 	<li>lowerLimit: empty
	 * 	<li>sampleSize: empty
	 */
	public static NewCharacteristicBuilder builder() {
		return new NewCharacteristicBuilder()
			.withArchived(false)
			.withUpperLimit(OptionalDouble.empty())
			.withLowerLimit(OptionalDouble.empty())
			.withSampleSize(OptionalInt.empty());
	}
}
