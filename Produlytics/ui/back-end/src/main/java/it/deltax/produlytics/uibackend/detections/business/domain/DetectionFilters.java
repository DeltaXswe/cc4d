package it.deltax.produlytics.uibackend.detections.business.domain;

import lombok.Builder;

import java.util.OptionalInt;
import java.util.OptionalLong;

/**
 * Record che rappresenta i filtri applicabili alla ricerca di una lista di rilevazioni.
 * Tutti i filtri possono non essere specificati.
 * Mette a disposizione un builder con valori di default.
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
	 * olderThan: empty
	 * newerThan: empty
	 * limit: empty
	 */
	public static DetectionFiltersBuilder builder() {
		return new DetectionFiltersBuilder()
			.withOlderThan(OptionalLong.empty())
			.withNewerThan(OptionalLong.empty())
			.withLimit(OptionalInt.empty());
	}
}
