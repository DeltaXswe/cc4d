package it.deltax.produlytics.api.repositories;

import java.util.Optional;

// Rappresenta i limiti "grezzi" di una caratteristica.
public interface LimitsEntity {
	// TODO: Vedere se Spring accetta OptionalDouble invece che Optional<Double>
	Optional<Double> getTechnicalLowerLimit();
	Optional<Double> getTechnicalUpperLimit();
	Optional<Double> getComputedMean();
	Optional<Double> getComputedStddev();
}
