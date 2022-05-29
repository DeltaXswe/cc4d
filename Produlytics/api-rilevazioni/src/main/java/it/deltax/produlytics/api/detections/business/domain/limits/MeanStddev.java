package it.deltax.produlytics.api.detections.business.domain.limits;

/**
 * Questo record rappresenta i limiti di processo di una caratteristica.
 *
 * @param mean la media calcolata dall'auto-adjust
 * @param mean la media calcolata dall'auto-adjust
 */
public record MeanStddev(double mean, double stddev) {}
