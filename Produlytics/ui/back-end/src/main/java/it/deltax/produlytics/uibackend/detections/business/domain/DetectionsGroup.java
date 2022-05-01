package it.deltax.produlytics.uibackend.detections.business.domain;

import java.util.List;
import java.util.OptionalLong;

/**
 * Record che rappresenta una lista di rilevazioni.
 * Comprende timestamp per ottenere liste rilevazioni più vecchie/recenti in nuova richiesta.
 */
public record DetectionsGroup(
    List<Detection> detections,
    OptionalLong nextOld,
    long nextNew
) {}
