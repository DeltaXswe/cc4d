package it.deltax.produlytics.uibackend.devices.business;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicDisplayInfo;
import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLimits;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetLimitsUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicLimitsPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindUnarchivedDevicePort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class GetLimitsService implements GetLimitsUseCase {
    private final FindCharacteristicLimitsPort findCharacteristicLimitsPort;

    public GetLimitsService(FindCharacteristicLimitsPort findCharacteristicLimitsPort) {
        this.findCharacteristicLimitsPort = findCharacteristicLimitsPort;
    }

    @Override
    public CharacteristicLimits getByCharacteristic(int deviceId, int characteristicId) throws BusinessException {
        return findCharacteristicLimitsPort.findByCharacteristic(deviceId, characteristicId)
            .orElseThrow(() -> new BusinessException("characteristicNotFound", ErrorType.NOT_FOUND));


    }
}
