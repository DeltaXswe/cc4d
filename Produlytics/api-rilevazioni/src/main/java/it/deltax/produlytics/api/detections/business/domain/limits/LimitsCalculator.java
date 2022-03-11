package it.deltax.produlytics.api.detections.business.domain.limits;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.control_chart.CalculatedLimits;

import java.util.List;

public interface LimitsCalculator {
	void add(Detection newDetection);
	void slide(Detection oldDetection, Detection newDetection);
	void reset(List<Detection> detections);
	CalculatedLimits getCalculatedLimits();
}
