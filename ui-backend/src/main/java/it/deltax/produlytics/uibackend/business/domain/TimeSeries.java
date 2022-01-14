package it.deltax.produlytics.uibackend.business.domain;

import java.math.BigDecimal;
import java.util.List;

public record TimeSeries(
        List<DetectionLight> timeSeries,
        BigDecimal lowerLimit,
        BigDecimal upperLimit,
        BigDecimal average,
        String machineName
) {
}
