package it.deltax.produlytics.uibackend.devices.business;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicTitle;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedCharacteristicsUseCase;
import it.deltax.produlytics.uibackend.devices.business.domain.out.FindAllUnarchivedCharacteristicsPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetUnarchivedCharacteristicsService implements GetUnarchivedCharacteristicsUseCase {

    private final FindAllUnarchivedCharacteristicsPort port;

    public GetUnarchivedCharacteristicsService(FindAllUnarchivedCharacteristicsPort port) {
        this.port = port;
    }

    @Override
    public List<CharacteristicTitle> getByDevice(int deviceId) throws BusinessException {
        var characteristics = port.findAllByDeviceId(deviceId);

        if (characteristics.isEmpty())
            throw new BusinessException("deviceNotFound", ErrorType.NOT_FOUND);

        return characteristics;
    }
}
