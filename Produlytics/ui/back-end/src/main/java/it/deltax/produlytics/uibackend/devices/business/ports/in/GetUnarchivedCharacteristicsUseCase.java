package it.deltax.produlytics.uibackend.devices.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicTitle;

import java.util.List;
import java.util.Optional;

public interface GetUnarchivedCharacteristicsUseCase {
    Optional<List<CharacteristicTitle>> getByDevice(int machineId);
}
