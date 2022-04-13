package it.deltax.produlytics.uibackend.detections.business.domain;

import java.util.List;
import java.util.OptionalLong;

/**
 * <p>Record che rappresenta una lista di rilevazioni
 * <p>Fornisce anche timestamp per ottenere le liste delle rilevazioni più vecchie o più nuove in una nuova richiesta
 */
public record Detections(
	List<Detection> detections, OptionalLong nextOld, long nextNew
) {}
