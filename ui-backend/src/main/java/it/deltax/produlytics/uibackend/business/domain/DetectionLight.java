package it.deltax.produlytics.uibackend.business.domain;

public record DetectionLight(
        double value,
        long createdAtUtc,
        boolean anomalous
) {
}
