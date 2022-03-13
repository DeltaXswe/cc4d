package it.deltax.produlytics.api.detections.business.domain;

// Informazioni relative a una rilevazione derivanti all'autenticazione.
public record DetectionInfo(
	int deviceId, LimitsInfo limitsInfo
) {}
