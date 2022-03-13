package it.deltax.produlytics.api.detections.business.domain;

import java.util.Optional;

// Informazioni di una caratteristica per calcolare i limiti.
public record LimitsInfo(
	Optional<Integer> sampleSize, Optional<Double> lowerLimit, Optional<Double> upperLimit, Optional<Double> mean
) {}
