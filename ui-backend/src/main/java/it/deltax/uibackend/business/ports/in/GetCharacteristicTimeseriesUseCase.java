package it.deltax.uibackend.business.ports.in;

import it.deltax.uibackend.business.domain.CharacteristicTimeseries;

import java.util.Optional;

public interface GetCharacteristicTimeseriesUseCase {

    Optional<CharacteristicTimeseries> getCharacteristicTimeSeries(long machine, String characteristic);
}
