package it.deltax.produlytics.uibackend.detections.business.domain;

import lombok.Builder;

import java.util.OptionalInt;
import java.util.OptionalLong;

/**
 * <p>Record che rappresenta i filtri applicabili alla ricerca di una lista di rilevazioni
 * <p>Tutti i filtri possono non essere specificati
 * <p>Mette a disposizione un builder con valori di default
 */
public record DetectionFilters(
	OptionalLong olderThan,
	OptionalLong newerThan,
	OptionalInt limit
) {
	@Builder(builderMethodName = "", setterPrefix = "with")
	public DetectionFilters {}

	/**
	 * Fornisce il builder del record
	 * @return un nuovo builder con i seguenti valori di default:
	 * <ul>
	 *     <li>olderThan: empty
	 * 	   <li>newerThan: empty
	 * 	   <li>limit: empty
	 */
	public static DetectionFiltersBuilder builder() {
		return new DetectionFiltersBuilder()
			.withOlderThan(OptionalLong.empty())
			.withNewerThan(OptionalLong.empty())
			.withLimit(OptionalInt.empty());
	}
}
