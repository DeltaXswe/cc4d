package it.deltax.produlytics.uibackend.business.domain;

import java.math.BigDecimal;

public record CharacteristicInfo(
        BigDecimal lowerLimit,
        BigDecimal upperLimit,
        BigDecimal average
) {
}
