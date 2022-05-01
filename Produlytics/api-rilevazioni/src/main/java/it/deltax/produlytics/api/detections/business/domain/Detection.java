package it.deltax.produlytics.api.detections.business.domain;

import java.time.Instant;

/**
 * Questo record rappresenta una rilevazione.
 *
 * @param characteristicId l'identificativo globale della caratteristica a cui
 *                         è associata la rilevazione
 * @param creationTime l'istante in cui è stata misurata la rilevazione
 * @param creationTime l'istante in cui è stata misurata la rilevazione
 */
public record Detection(CharacteristicId characteristicId, Instant creationTime, double value) {}
