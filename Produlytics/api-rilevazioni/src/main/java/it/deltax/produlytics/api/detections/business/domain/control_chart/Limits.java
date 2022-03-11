package it.deltax.produlytics.api.detections.business.domain.control_chart;

import java.util.Optional;

public record Limits(Optional<CalculatedLimits> calculatedLimits, Optional<FixedLimits> fixedLimits) {
	public record CalculatedLimits(double mean, double variance) {}

	public record FixedLimits(double upperLimit, double lowerLimit, double mean) {}
}
