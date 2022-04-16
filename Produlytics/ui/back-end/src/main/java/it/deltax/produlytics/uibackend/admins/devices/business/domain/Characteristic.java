package it.deltax.produlytics.uibackend.admins.devices.business.domain;

import lombok.Builder;

/**
 * <p>Record che rappresenta l'intestazione di una caratteristica, con
 * 	l'id, il nome e il valore di <code>archived</code>
 * <p>Mette a disposizione un builder con valori di default
 */
public record Characteristic(
	int id,
	String name,
	boolean archived
) {
	@Builder(toBuilder = true, builderMethodName = "", setterPrefix = "with")
	public Characteristic {}

	/**
	 * Fornisce il builder del record
	 * @return un nuovo builder con <code>archived</code> inizializzato a <code>false</code> di default
	 */
	public static CharacteristicBuilder builder() {
		return new CharacteristicBuilder()
			.withArchived(false);
	}
}
