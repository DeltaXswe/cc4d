package it.deltax.produlytics.uibackend.common.detection.business.domain;

public record DetectionLight(
	double value,
	long createdAtUtc,
	boolean anomalous
) {
}
