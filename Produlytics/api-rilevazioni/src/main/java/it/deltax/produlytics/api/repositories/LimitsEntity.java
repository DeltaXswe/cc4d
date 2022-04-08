package it.deltax.produlytics.api.repositories;

import java.util.Optional;

// Rappresenta i limiti "grezzi" di una caratteristica.
public interface LimitsEntity {
	boolean getAutoAdjust();
	Optional<Double> getTechnicalLowerLimit();
	Optional<Double> getTechnicalUpperLimit();
	double getComputedMean();
	double getComputedStddev();
}
