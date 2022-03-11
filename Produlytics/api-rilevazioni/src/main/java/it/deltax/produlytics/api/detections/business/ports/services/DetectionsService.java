package it.deltax.produlytics.api.detections.business.ports.services;

import it.deltax.produlytics.api.detections.business.domain.DetectionInfo;
import it.deltax.produlytics.api.detections.business.domain.IncomingDetection;
import it.deltax.produlytics.api.detections.business.domain.RawDetection;
import it.deltax.produlytics.api.detections.business.domain.exception.CharacteristicArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.CharacteristicNotFoundException;
import it.deltax.produlytics.api.detections.business.domain.exception.DeviceArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.NotAuthenticatedException;
import it.deltax.produlytics.api.detections.business.domain.queue.DetectionQueue;
import it.deltax.produlytics.api.detections.business.domain.validate.DetectionValidator;
import it.deltax.produlytics.api.detections.business.ports.in.ProcessIncomingDetectionUseCase;

import java.time.Instant;

public class DetectionsService implements ProcessIncomingDetectionUseCase {
	private final DetectionValidator detectionValidator;
	private final DetectionQueue detectionQueue;

	public DetectionsService(DetectionValidator detectionValidator, DetectionQueue detectionQueue) {
		this.detectionValidator = detectionValidator;
		this.detectionQueue = detectionQueue;
	}

	@Override
	public void processIncomingDetection(IncomingDetection incomingDetection) throws
		CharacteristicArchivedException,
		CharacteristicNotFoundException,
		DeviceArchivedException,
		NotAuthenticatedException {
		DetectionInfo detectionInfo = detectionValidator.validateAndFindDeviceId(incomingDetection.apiKey(),
			incomingDetection.characteristicId()
		);
		RawDetection rawDetection = new RawDetection(detectionInfo.deviceId(),
			incomingDetection.characteristicId(),
			Instant.now(),
			incomingDetection.value(),
			detectionInfo.limitsInfo()
		);
		detectionQueue.enqueueDetection(rawDetection);
	}
}
