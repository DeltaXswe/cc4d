package it.deltax.uibackend.business.domain;

import java.math.BigDecimal;

public record CharacteristicInfo(
        BigDecimal limiteMin,
        BigDecimal limiteMax,
        BigDecimal media
) {
}
