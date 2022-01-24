package it.deltax.produlytics.api.business.domain;


// business class for light lookups

public record DetectionLight(long machine, String characteristic, double value, boolean anomalous) {
}
