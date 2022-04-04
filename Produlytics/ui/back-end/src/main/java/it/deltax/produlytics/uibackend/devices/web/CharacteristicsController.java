package it.deltax.produlytics.uibackend.devices.web;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLimits;
import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicTitle;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetLimitsUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedCharacteristicsUseCase;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/devices/{id}/characteristics")
public class CharacteristicsController {
	private final GetUnarchivedCharacteristicsUseCase getUnarchivedCharacteristics;
	private final GetLimitsUseCase getLimitsUseCase;

	public CharacteristicsController(
		GetUnarchivedCharacteristicsUseCase getUnarchivedCharacteristics, GetLimitsUseCase getLimitsUseCase
	) {
		this.getUnarchivedCharacteristics = getUnarchivedCharacteristics;
		this.getLimitsUseCase = getLimitsUseCase;
	}

    @GetMapping("")
    ResponseEntity<List<CharacteristicTitle>> getUnarchivedCharacteristics(
        @PathVariable("id") int deviceId
    ) throws BusinessException {
        return new ResponseEntity<>(
            getUnarchivedCharacteristics.getByDevice(deviceId),
            HttpStatus.OK
        );
    }

	@GetMapping("{characteristicId}/limits")
	ResponseEntity<CharacteristicLimits> getCharacteristicLimits(
        @PathVariable("id") int deviceId,
        @PathVariable("characteristicId") int characteristicId
    ) throws BusinessException {
        return new ResponseEntity<>(
            getLimitsUseCase.getByCharacteristic(deviceId, characteristicId),
            HttpStatus.OK
        );
    }
}
