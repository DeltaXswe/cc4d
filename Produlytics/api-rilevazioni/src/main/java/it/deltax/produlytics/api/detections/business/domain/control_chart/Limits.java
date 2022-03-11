package it.deltax.produlytics.api.detections.business.domain.control_chart;

import java.util.Optional;

public record Limits(
	Optional<CalculatedLimits> calculatedLimits,
	Optional<Double> lowerLimit,
	Optional<Double> upperLimit,
	Optional<Double> mean
)
{}
