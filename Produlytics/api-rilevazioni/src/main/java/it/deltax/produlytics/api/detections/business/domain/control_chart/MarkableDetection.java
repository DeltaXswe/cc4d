package it.deltax.produlytics.api.detections.business.domain.control_chart;

public interface MarkableDetection {
	double getValue();
	void markOutlier();
}
