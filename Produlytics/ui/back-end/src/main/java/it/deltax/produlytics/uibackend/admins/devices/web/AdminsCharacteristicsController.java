package it.deltax.produlytics.uibackend.admins.devices.web;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.InsertCharacteristicUseCase;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.OptionalDouble;
import java.util.OptionalInt;

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
		@RequestParam(value = "name") String name,
		@RequestParam(value = "lowerLimit", required = false) Double lowerLimit,
		@RequestParam(value = "upperLimit", required = false) Double upperLimit,
		@RequestParam(value = "autoAdjust") boolean autoAdjust,
		@RequestParam(value = "sampleSize", required = false) Integer sampleSize
	) throws BusinessException {
		var builder = NewCharacteristic.toBuilder()
			.withName(name)
			.withAutoAdjust(autoAdjust);

		if (lowerLimit != null)
			builder = builder.withLowerLimit(OptionalDouble.of(lowerLimit));
		if (upperLimit != null)
			builder = builder.withUpperLimit(OptionalDouble.of(upperLimit));
		if (sampleSize != null)
			builder = builder.withSampleSize(OptionalInt.of(sampleSize));

		return ResponseEntity.ok(Map.of(
			"id",
			insertCharacteristic.insertByDevice(deviceId, builder.build())
		));
	}
}
