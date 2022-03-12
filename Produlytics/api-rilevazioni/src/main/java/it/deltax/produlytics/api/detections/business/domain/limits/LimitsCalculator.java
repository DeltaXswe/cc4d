package it.deltax.produlytics.api.detections.business.domain.limits;

import it.deltax.produlytics.api.detections.business.domain.control_chart.CalculatedLimits;

import java.util.List;

public interface LimitsCalculator {
	void add(double newValue);
	void slide(double oldValue, double newvalue);
	void reset(List<Double> values);
	CalculatedLimits getCalculatedLimits();
}
