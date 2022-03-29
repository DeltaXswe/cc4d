package it.deltax.produlytics.uibackend.devices.business.domain;

import java.util.Optional;

public record CharacteristicLimits(
	double lowerLimit,
	double upperLimit,
	Optional<Double> mean
) {}
