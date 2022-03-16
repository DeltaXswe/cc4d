package it.deltax.produlytics.api.repositories;

import java.util.Optional;

public interface LimitsEntity {
	Optional<Double> technicalLowerLimit();
	Optional<Double> technicalUpperLimit();
	Optional<Double> computedMean();
	Optional<Double> computedStddev();
}
