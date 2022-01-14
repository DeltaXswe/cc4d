package it.deltax.produlytics.uibackend.business.ports.in;

import it.deltax.produlytics.uibackend.business.domain.TimeSeries;

import java.util.Optional;

public interface TimeSeriesUseCase {
    Optional<TimeSeries> getTimeSeries(long machine, String characteristic);
}
