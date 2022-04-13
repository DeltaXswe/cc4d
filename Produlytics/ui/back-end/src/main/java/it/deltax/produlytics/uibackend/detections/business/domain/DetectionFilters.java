package it.deltax.produlytics.uibackend.detections.business.domain;

import java.util.OptionalInt;
import java.util.OptionalLong;

/**
 * <p>Record che rappresenta i filtri applicabili alla ricerca di una lista di rilevazioni
 * <p>Tutti i filtri possono non essere specificati
 */
public record DetectionFilters(
	OptionalLong olderThan, OptionalLong newerThan, OptionalInt limit
) {}
