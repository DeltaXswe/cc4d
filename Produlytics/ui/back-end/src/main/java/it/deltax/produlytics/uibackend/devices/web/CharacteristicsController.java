package it.deltax.produlytics.uibackend.devices.web;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicDisplayInfo;
import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicTitle;
import it.deltax.produlytics.uibackend.devices.business.ports.in.FindCharacteristicInfoUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedCharacteristicsUseCase;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devices/{deviceId}/characteristics")
public class CharacteristicsController {
    private final GetUnarchivedCharacteristicsUseCase getUnarchivedCharacteristics;
    private final FindCharacteristicInfoUseCase getCharacteristicInfo;

    public CharacteristicsController(
        GetUnarchivedCharacteristicsUseCase getUnarchivedCharacteristics,
        FindCharacteristicInfoUseCase getCharacteristicInfo
    ) {
        this.getUnarchivedCharacteristics = getUnarchivedCharacteristics;
        this.getCharacteristicInfo = getCharacteristicInfo;
    }

    @GetMapping("")
    Iterable<CharacteristicTitle> getUnarchivedCharacteristics(@PathVariable("deviceId") int deviceId)
    throws BusinessException {
        return getUnarchivedCharacteristics.getByDevice(deviceId);
    }

    @GetMapping("{id}")
	CharacteristicDisplayInfo getCharacteristicInfo(
        @PathVariable("deviceId") int deviceId,
        @PathVariable("id") int id
    ) throws BusinessException {
        return getCharacteristicInfo.find(deviceId, id)
            .orElseThrow(
                () -> {return new BusinessException("characteristicNotFound", ErrorType.NOT_FOUND);}
            );
    }
}
