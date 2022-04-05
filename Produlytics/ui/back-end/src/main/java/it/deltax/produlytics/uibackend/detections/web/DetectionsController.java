package it.deltax.produlytics.uibackend.detections.web;

import it.deltax.produlytics.uibackend.detections.business.domain.DetectionLight;
import it.deltax.produlytics.uibackend.detections.business.ports.in.ListDetectionsByCharacteristicUseCase;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/devices/{deviceId}/characteristics/{characteristicId}/detections")
public class DetectionsController {
	private final ListDetectionsByCharacteristicUseCase listDetectionsByCharacteristic;

	public DetectionsController(
		@Qualifier("getListDetectionsByCharacteristicUseCase")
			ListDetectionsByCharacteristicUseCase listDetectionsByCharacteristic
	) {
		this.listDetectionsByCharacteristic = listDetectionsByCharacteristic;
	}

	@GetMapping("")
	public List<DetectionLight> getCharacteristicDetections(
		@PathVariable int deviceId, @PathVariable int characteristicId, @RequestParam("createdAfter") Long createdAfter
	) {
		return listDetectionsByCharacteristic.listByCharacteristic(deviceId, characteristicId, createdAfter);
	}
}
