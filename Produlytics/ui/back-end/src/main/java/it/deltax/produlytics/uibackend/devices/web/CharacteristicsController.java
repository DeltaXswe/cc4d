package it.deltax.produlytics.uibackend.devices.web;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicDisplayInfo;
import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicTitle;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetLimitsUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedCharacteristicsUseCase;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/devices/{deviceId}/characteristics")
public class CharacteristicsController {
    private final GetUnarchivedCharacteristicsUseCase getUnarchivedCharacteristics;
    private final GetLimitsUseCase getCharacteristicInfo;

    public CharacteristicsController(
        GetUnarchivedCharacteristicsUseCase getUnarchivedCharacteristics,
        GetLimitsUseCase getCharacteristicInfo
    ) {
        this.getUnarchivedCharacteristics = getUnarchivedCharacteristics;
        this.getCharacteristicInfo = getCharacteristicInfo;
    }

    @GetMapping("")
    ResponseEntity<List<CharacteristicTitle>> getUnarchivedCharacteristics(
        @PathVariable("deviceId") int deviceId
    ) throws BusinessException {
        return new ResponseEntity<>(getUnarchivedCharacteristics.getByDevice(deviceId), HttpStatus.OK);
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
