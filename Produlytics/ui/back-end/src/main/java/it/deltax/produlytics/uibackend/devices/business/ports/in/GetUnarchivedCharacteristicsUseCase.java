package it.deltax.produlytics.uibackend.devices.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicTitle;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

public interface GetUnarchivedCharacteristicsUseCase {
    Iterable<CharacteristicTitle> getByDevice(int deviceId) throws BusinessException;
}
