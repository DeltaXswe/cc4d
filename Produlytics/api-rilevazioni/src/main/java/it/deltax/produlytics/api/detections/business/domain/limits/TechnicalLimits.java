package it.deltax.produlytics.api.detections.business.domain.limits;

/**
 * Questo record rappresenta i limiti tecnici di una caratteristica.
 *
 * @param lowerLimit Il limite tecnico inferiore.
 * @param upperLimit Il limite tecnico superiore.
 */
public record TechnicalLimits(double lowerLimit, double upperLimit) {}
