package it.deltax.produlytics.api.detections.business.domain;

public record RawDetection(
	int deviceId,
	int characteristicId,
	java.time.Instant creationTime,
	double value,
	LimitsInfo limitsInfo
) {}
