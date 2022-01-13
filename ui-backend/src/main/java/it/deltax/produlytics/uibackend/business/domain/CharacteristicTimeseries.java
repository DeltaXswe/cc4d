package it.deltax.produlytics.uibackend.business.domain;

import java.math.BigDecimal;
import java.util.List;

public record CharacteristicTimeseries(
        List<TimeseriesPointLight> timeseries,
        BigDecimal limite_max,
        BigDecimal limite_min,
        BigDecimal media,
        String nome_macchina
) {
}
