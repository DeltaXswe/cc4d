package it.deltax.produlytics.uibackend.business.ports.in;

import it.deltax.produlytics.uibackend.business.domain.CharacteristicTimeseries;

import java.util.Optional;

public interface GetCharacteristicTimeseriesUseCase {

    Optional<CharacteristicTimeseries> getCharacteristicTimeSeries(long machine, String characteristic);
}
