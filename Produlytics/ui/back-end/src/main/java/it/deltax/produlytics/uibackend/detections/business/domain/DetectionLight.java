package it.deltax.produlytics.uibackend.detections.business.domain;

public record DetectionLight(
	double value,
	long createdAtUtc,
	boolean anomalous
) {
}
