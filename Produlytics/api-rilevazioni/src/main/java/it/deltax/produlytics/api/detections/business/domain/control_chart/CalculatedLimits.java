package it.deltax.produlytics.api.detections.business.domain.control_chart;

// Rappresenta i valori calcolati dall'auto-adjust, cioè media e deviazione standard.
public record CalculatedLimits(double mean, double deviation) {}
