package it.deltax.produlytics.api.repositories;

import java.util.Optional;

// Rappresenta i limiti "grezzi" di una caratteristica.
public interface LimitsEntity {
	// TODO: Vedere se Spring accetta OptionalDouble invece che Optional<Double>
	boolean getAutoAdjust();
	Optional<Double> getTechnicalLowerLimit();
	Optional<Double> getTechnicalUpperLimit();
	double getComputedMean();
	double getComputedStddev();
}
