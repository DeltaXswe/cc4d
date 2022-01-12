package it.deltax.uibackend.business.domain;

public record TimeseriesPointLight(
        double valore,
        long creazione_utc,
        boolean anomalo
) {
}
