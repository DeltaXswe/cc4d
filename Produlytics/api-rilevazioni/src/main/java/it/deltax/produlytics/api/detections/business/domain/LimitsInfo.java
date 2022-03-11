package it.deltax.produlytics.api.detections.business.domain;

import java.util.Optional;

public record LimitsInfo(
	Optional<Integer> sampleSize,
	Optional<Double> lowerLimit,
	Optional<Double> upperLimit,
	Optional<Double> mean
) {}
