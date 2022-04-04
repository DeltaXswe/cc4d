package it.deltax.produlytics.api.detections.business.domain.charts;

// Rappresenta una rilevazione che può essere marcata come anomala.
public interface MarkableDetection {
	double value();
	void markOutlier();
}
