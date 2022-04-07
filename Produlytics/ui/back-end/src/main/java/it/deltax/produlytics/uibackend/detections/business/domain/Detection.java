package it.deltax.produlytics.uibackend.detections.business.domain;

public record Detection(
	long creationTime, double value, boolean outlier
) {}
