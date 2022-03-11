package it.deltax.produlytics.api.detections.business.domain.limits;

import it.deltax.produlytics.api.detections.business.domain.Detection;

import java.util.List;

public interface LimitsCalculatorFactory {
	LimitsCalculator createCalculator();
}
