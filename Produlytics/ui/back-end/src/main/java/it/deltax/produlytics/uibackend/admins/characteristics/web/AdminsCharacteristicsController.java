package it.deltax.produlytics.uibackend.admins.characteristics.web;

import it.deltax.produlytics.uibackend.admins.characteristics.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.admins.characteristics.business.ports.in.InsertCharacteristicUseCase;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/admins/devices/{deviceId}/characteristics")
public class AdminsCharacteristicsController {
	private final InsertCharacteristicUseCase insertCharacteristic;

	AdminsCharacteristicsController(InsertCharacteristicUseCase insertCharacteristic) {
		this.insertCharacteristic = insertCharacteristic;
	}

	@PostMapping("")
	public ResponseEntity<Map<String, Integer>> insertCharacteristic(
		@PathVariable("deviceId") int deviceId,
		@RequestBody(required = false) NewCharacteristic characteristic
	) throws BusinessException {
		return ResponseEntity.ok(Map.of(
			"id",
			insertCharacteristic.insertByDevice(deviceId, characteristic)
		));
	}
}
