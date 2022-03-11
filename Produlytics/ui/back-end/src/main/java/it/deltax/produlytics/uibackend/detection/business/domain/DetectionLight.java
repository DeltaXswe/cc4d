package it.deltax.produlytics.uibackend.detection.business.domain;

public record DetectionLight(
	double value,
	long createdAtUtc,
	boolean anomalous
) {
}
