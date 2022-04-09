package it.deltax.produlytics.api.detections.business.ports.services;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.IncomingDetection;
import it.deltax.produlytics.api.detections.business.domain.queue.DetectionQueue;
import it.deltax.produlytics.api.detections.business.domain.validate.DetectionValidator;
import it.deltax.produlytics.api.detections.business.ports.in.ProcessIncomingDetectionUseCase;
import it.deltax.produlytics.api.exceptions.BusinessException;

import java.time.Instant;

// Implementazione di riferimento di `ProcessIncomingDetectionUseCase`.
public class DetectionsService implements ProcessIncomingDetectionUseCase {
	private final DetectionValidator detectionValidator;
	private final DetectionQueue detectionQueue;

	public DetectionsService(DetectionValidator detectionValidator, DetectionQueue detectionQueue) {
		this.detectionValidator = detectionValidator;
		this.detectionQueue = detectionQueue;
	}

	@Override
	public void processIncomingDetection(IncomingDetection incomingDetection) throws BusinessException {
		CharacteristicId characteristicId = detectionValidator.validateAndFindId(incomingDetection.apiKey(),
			incomingDetection.characteristic()
		);
		Detection detection = new Detection(characteristicId, Instant.now(), incomingDetection.value());
		detectionQueue.enqueueDetection(detection);
	}
}
