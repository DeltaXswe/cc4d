package it.deltax.produlytics.uibackend.devices.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyCharacteristic;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

import java.util.List;

public interface GetUnarchivedCharacteristicsUseCase {
    List<TinyCharacteristic> getByDevice(int deviceId) throws BusinessException;
}
