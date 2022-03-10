package it.deltax.produlytics.api.detections.business.domain;

import java.time.Instant;

public record Detection(int deviceId, int characteristicId, Instant creationTime, double value, boolean outlier) {}
