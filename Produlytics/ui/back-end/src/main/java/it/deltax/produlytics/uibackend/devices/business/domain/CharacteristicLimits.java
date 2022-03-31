package it.deltax.produlytics.uibackend.devices.business.domain;

import java.util.OptionalDouble;

public record CharacteristicLimits(
	double lowerLimit,
	double upperLimit,
	OptionalDouble mean
) {}
