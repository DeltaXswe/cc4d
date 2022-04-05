package it.deltax.produlytics.uibackend.detections.business.domain;

import java.time.Instant;

public record Detection(
	Instant creationTime, double value, boolean outlier
) {}
