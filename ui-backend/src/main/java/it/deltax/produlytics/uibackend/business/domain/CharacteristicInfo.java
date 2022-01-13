package it.deltax.produlytics.uibackend.business.domain;

import java.math.BigDecimal;

public record CharacteristicInfo(
        BigDecimal limiteMin,
        BigDecimal limiteMax,
        BigDecimal media
) {
}
