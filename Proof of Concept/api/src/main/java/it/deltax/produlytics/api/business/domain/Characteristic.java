package it.deltax.produlytics.api.business.domain;

public record Characteristic(
        String code,
        String name,
        long machine,
        Double lowerLimit,
        Double upperLimit,
        Double average
) {
}
