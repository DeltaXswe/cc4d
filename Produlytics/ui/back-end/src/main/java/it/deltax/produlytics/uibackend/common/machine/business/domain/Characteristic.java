package it.deltax.produlytics.uibackend.common.machine.business.domain;

import java.math.BigDecimal;
import java.util.Optional;

public record Characteristic(
	long id,
	String name,
	long machine,
	Optional<Double> lowerLimit,
	Optional<Double> upperLimit,
	Optional<Double> average
) {
}
