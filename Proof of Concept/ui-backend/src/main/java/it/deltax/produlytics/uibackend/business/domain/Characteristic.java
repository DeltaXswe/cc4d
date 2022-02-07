package it.deltax.produlytics.uibackend.business.domain;

import java.math.BigDecimal;

public record Characteristic(
        String code,
        String name,
        long machine,
        BigDecimal lowerLimit,
        BigDecimal upperLimit,
        BigDecimal average
) {
}
