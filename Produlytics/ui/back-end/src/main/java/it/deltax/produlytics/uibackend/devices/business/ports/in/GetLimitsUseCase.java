package it.deltax.produlytics.uibackend.devices.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLimits;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

public interface GetLimitsUseCase {
    CharacteristicLimits getByCharacteristic(int deviceId, int characteristicsId) throws BusinessException;
}
