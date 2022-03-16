package it.deltax.produlytics.api.detections.business.ports.services;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.IncomingDetection;
import it.deltax.produlytics.api.detections.business.domain.ValidationInfo;
import it.deltax.produlytics.api.detections.business.domain.exception.ArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.NotAuthenticatedException;
import it.deltax.produlytics.api.detections.business.domain.exception.NotFoundException;
import it.deltax.produlytics.api.detections.business.domain.queue.DetectionQueue;
import it.deltax.produlytics.api.detections.business.domain.validate.DetectionValidator;
import it.deltax.produlytics.api.detections.business.ports.in.ProcessIncomingDetectionUseCase;

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
	public void processIncomingDetection(IncomingDetection incomingDetection)
	throws ArchivedException, NotFoundException, NotAuthenticatedException {
		ValidationInfo validationInfo = detectionValidator.validateAndFindDeviceId(incomingDetection.apiKey(),
			incomingDetection.characteristicId()
		);
		Detection detection = new Detection(validationInfo.deviceId(),
			incomingDetection.characteristicId(),
			Instant.now(),
			incomingDetection.value()
		);
		detectionQueue.enqueueDetection(detection);
	}
}
