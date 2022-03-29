package it.deltax.produlytics.uibackend.devices.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicTitle;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;

import java.util.List;

public interface GetUnarchivedCharacteristicsUseCase {
    List<CharacteristicTitle> getByDevice(int deviceId) throws BusinessException;
}
