package it.deltax.produlytics.api.detections.business.domain;

import java.time.Instant;

// Rilevazione memorizzata, senza outlier.
public record Detection(int deviceId, int characteristicId, Instant creationTime, double value) {}
