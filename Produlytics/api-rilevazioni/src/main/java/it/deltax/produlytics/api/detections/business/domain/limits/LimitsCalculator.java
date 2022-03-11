package it.deltax.produlytics.api.detections.business.domain.limits;

import it.deltax.produlytics.api.detections.business.domain.Detection;

import java.util.List;

public interface LimitsCalculator {
	void update(Detection newDetection);
	void update(Detection oldDetection, Detection newDetection);
	void reset(List<Detection> detections);
	double getMean();
	double getDeviation();
}
