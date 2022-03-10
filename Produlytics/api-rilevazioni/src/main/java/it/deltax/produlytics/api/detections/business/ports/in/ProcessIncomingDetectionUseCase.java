package it.deltax.produlytics.api.detections.business.ports.in;

import it.deltax.produlytics.api.detections.business.domain.IncomingDetection;
import it.deltax.produlytics.api.detections.business.domain.exception.CharacteristicArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.CharacteristicNotFoundException;
import it.deltax.produlytics.api.detections.business.domain.exception.DeviceArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.NotAuthenticatedException;

public interface ProcessIncomingDetectionUseCase {
	void processIncomingDetection(IncomingDetection incomingDetection) throws
		CharacteristicArchivedException,
		CharacteristicNotFoundException,
		DeviceArchivedException,
		NotAuthenticatedException;
}
