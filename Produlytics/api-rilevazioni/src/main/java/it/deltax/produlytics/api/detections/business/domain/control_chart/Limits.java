package it.deltax.produlytics.api.detections.business.domain.control_chart;

import java.util.Optional;

// Rappresenta i limiti e la media di una caratteristica, utilizzabili per l'applicazione delle carte di controllo.
public record Limits(
	Optional<CalculatedLimits> calculatedLimits,
	Optional<Double> lowerLimit,
	Optional<Double> upperLimit,
	Optional<Double> mean
)
{}
