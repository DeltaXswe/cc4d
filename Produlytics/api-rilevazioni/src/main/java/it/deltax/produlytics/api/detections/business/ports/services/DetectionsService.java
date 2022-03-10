package it.deltax.produlytics.api.detections.business.ports.services;

import it.deltax.produlytics.api.detections.business.domain.IncomingDetection;
import it.deltax.produlytics.api.detections.business.domain.exception.CharacteristicArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.CharacteristicNotFoundException;
import it.deltax.produlytics.api.detections.business.domain.exception.DeviceArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.NotAuthenticatedException;
import it.deltax.produlytics.api.detections.business.domain.queue.DetectionQueue;
import it.deltax.produlytics.api.detections.business.domain.validate.DetectionValidator;
import it.deltax.produlytics.api.detections.business.ports.in.ProcessIncomingDetectionUseCase;

@SuppressWarnings("ALL")
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
		int deviceId = detectionValidator.validateAndFindDeviceId(incomingDetection.apiKey(),
			incomingDetection.characteristicId()
		);
		detectionQueue.enqueueDetection(incomingDetection.toRawDetection(deviceId));
	}
}
