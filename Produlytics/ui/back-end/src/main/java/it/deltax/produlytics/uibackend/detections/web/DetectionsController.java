package it.deltax.produlytics.uibackend.detections.web;

import it.deltax.produlytics.uibackend.detections.business.domain.DetectionFilters;
import it.deltax.produlytics.uibackend.detections.business.domain.Detections;
import it.deltax.produlytics.uibackend.detections.business.ports.in.GetDetectionsUseCase;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.web.bind.annotation.*;

import java.util.OptionalInt;
import java.util.OptionalLong;

@RestController
@RequestMapping("/devices/{deviceId}/characteristics/{characteristicId}/detections")
public class DetectionsController {
	private final GetDetectionsUseCase listDetectionsByCharacteristic;

	public DetectionsController(GetDetectionsUseCase listDetectionsByCharacteristic) {
		this.listDetectionsByCharacteristic = listDetectionsByCharacteristic;
	}

	@GetMapping("")
	public Detections getCharacteristicDetections(
		@PathVariable int deviceId,
		@PathVariable int characteristicId,
		@RequestParam(value = "olderThan", required = false) Long olderThan,
		@RequestParam(value = "newerThan", required = false) Long newerThan,
		@RequestParam(value = "limit", required = false) Integer limit
	) throws BusinessException {
		return this.listDetectionsByCharacteristic.listByCharacteristic(
			deviceId, characteristicId, new DetectionFilters(
			olderThan != null ? OptionalLong.of(olderThan) : OptionalLong.empty(),
			newerThan != null ? OptionalLong.of(newerThan) : OptionalLong.empty(),
			limit != null ? OptionalInt.of(limit) : OptionalInt.empty()
		));
	}
}
