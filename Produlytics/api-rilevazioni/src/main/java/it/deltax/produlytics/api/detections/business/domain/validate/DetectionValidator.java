package it.deltax.produlytics.api.detections.business.domain.validate;

import it.deltax.produlytics.api.detections.business.domain.exception.CharacteristicArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.CharacteristicNotFoundException;
import it.deltax.produlytics.api.detections.business.domain.exception.DeviceArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.NotAuthenticatedException;

public interface DetectionValidator {
	int validateAndFindDeviceId(String apiKey, int characteristicId) throws
		NotAuthenticatedException,
		CharacteristicNotFoundException,
		CharacteristicArchivedException,
		DeviceArchivedException;
}
