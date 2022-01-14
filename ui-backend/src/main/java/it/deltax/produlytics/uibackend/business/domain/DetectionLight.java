package it.deltax.produlytics.uibackend.business.domain;

public record DetectionLight(
        double valore,
        long creazione_utc,
        boolean anomalo
) {
}
