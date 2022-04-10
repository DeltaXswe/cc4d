package it.deltax.produlytics.uibackend.admins.devices.web;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.InsertCharacteristicUseCase;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admins/devices/{deviceId}/characteristics")
public class AdminsCharacteristicsController {
	private final InsertCharacteristicUseCase insertCharacteristicUseCase;


	/**
	 * Il costruttore
	 * @param insertCharacteristic l'interfaccia per il caso d'uso d'inserimento di una caratteristica
	 */
	AdminsCharacteristicsController(InsertCharacteristicUseCase insertCharacteristic) {
		this.insertCharacteristicUseCase = insertCharacteristic;
	}


	@PostMapping("")
	public ResponseEntity<Map<String, Integer>> insertCharacteristic(
		@PathVariable("deviceId") int deviceId,
		@RequestBody(required = false) NewCharacteristic characteristic
	) throws BusinessException {
		return ResponseEntity.ok(Map.of(
			"id",
			this.insertCharacteristicUseCase.insertByDevice(deviceId, characteristic)
		));
	}
}
