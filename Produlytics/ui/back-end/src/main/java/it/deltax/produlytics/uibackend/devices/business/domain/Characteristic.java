package it.deltax.produlytics.uibackend.devices.business.domain;

import java.util.Optional;

public record Characteristic(
	long id, //int
	String name,
	int device,
	Optional<Double> lowerLimit,
	Optional<Double> upperLimit,
	Optional<Double> average
) {
}
