package it.deltax.produlytics.uibackend.detections.business.domain;

import java.util.List;
import java.util.OptionalLong;

/**
 * <p>Record che rappresenta una lista di rilevazioni
 * <p>Fornisce anche timestamp per ottenere le liste delle rilevazioni più vecchie o più recenti in una nuova richiesta
 */
public record DetectionsGroup(
	List<Detection> detections,
	OptionalLong nextOld,
	long nextNew
) {}
