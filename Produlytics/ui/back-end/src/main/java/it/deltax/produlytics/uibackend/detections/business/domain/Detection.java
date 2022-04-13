package it.deltax.produlytics.uibackend.detections.business.domain;

/**
 * Record che rappresenta tutte le informazioni di una rilevazione
 */
public record Detection(
	long creationTime, double value, boolean outlier
) {}
