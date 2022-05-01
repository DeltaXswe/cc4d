package it.deltax.produlytics.api.detections.business.domain.limits;

/**
 * Questo record rappresenta i limiti tecnici di una caratteristica.
 *
 * @param lowerLimit il limite tecnico inferiore
 * @param lowerLimit il limite tecnico inferiore
 */
public record TechnicalLimits(double lowerLimit, double upperLimit) {}
