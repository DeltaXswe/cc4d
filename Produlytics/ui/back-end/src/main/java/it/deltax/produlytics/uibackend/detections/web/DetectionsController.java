package it.deltax.produlytics.uibackend.detections.web;

import it.deltax.produlytics.uibackend.detections.business.domain.DetectionFilters;
import it.deltax.produlytics.uibackend.detections.business.domain.Detections;
import it.deltax.produlytics.uibackend.detections.business.ports.in.GetDetectionsUseCase;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.Optional;
import java.util.OptionalInt;

@RestController
@RequestMapping("/devices/{deviceId}/characteristics/{characteristicId}/detections")
public class DetectionsController {
	private final GetDetectionsUseCase listDetectionsByCharacteristic;

	public DetectionsController(
		@Qualifier("getListDetectionsByCharacteristicUseCase") GetDetectionsUseCase listDetectionsByCharacteristic
	) {
		this.listDetectionsByCharacteristic = listDetectionsByCharacteristic;
	}

	@GetMapping("")
	public Detections getCharacteristicDetections(
		@PathVariable int deviceId,
		@PathVariable int characteristicId,
		@RequestParam("olderThan") long olderThan,
		@RequestParam("newerThan") long newerThan,
		@RequestParam("maxNumDetections") int limit
	) throws BusinessException {
		return listDetectionsByCharacteristic.listByCharacteristic(deviceId, characteristicId, new DetectionFilters(
			Optional.of(Instant.ofEpochMilli(olderThan)),
			Optional.of(Instant.ofEpochMilli(newerThan)),
			OptionalInt.of(limit)
		));
	}
}
