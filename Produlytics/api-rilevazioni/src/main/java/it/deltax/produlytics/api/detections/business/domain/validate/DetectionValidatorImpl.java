package it.deltax.produlytics.api.detections.business.domain.validate;

import it.deltax.produlytics.api.detections.business.domain.exception.CharacteristicArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.CharacteristicNotFoundException;
import it.deltax.produlytics.api.detections.business.domain.exception.DeviceArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.NotAuthenticatedException;
import it.deltax.produlytics.api.detections.business.ports.out.FindCharacteristicValidationPort;
import it.deltax.produlytics.api.detections.business.ports.out.FindDeviceValidationByApiKeyPort;

public class DetectionValidatorImpl implements DetectionValidator {
	private final FindDeviceValidationByApiKeyPort findDeviceValidationByApiKeyPort;
	private final FindCharacteristicValidationPort findCharacteristicValidationPort;

	public DetectionValidatorImpl(
		FindDeviceValidationByApiKeyPort findDeviceValidationByApiKeyPort,
		FindCharacteristicValidationPort findCharacteristicValidationPort
	) {
		this.findDeviceValidationByApiKeyPort = findDeviceValidationByApiKeyPort;
		this.findCharacteristicValidationPort = findCharacteristicValidationPort;
	}

	@Override
	public int validateAndFindDeviceId(String apiKey, int characteristicId) throws
		CharacteristicNotFoundException,
		CharacteristicArchivedException,
		DeviceArchivedException,
		NotAuthenticatedException {

		DeviceValidationInfo deviceValidationInfo = this.findDeviceValidationByApiKeyPort.findDeviceByApiKey(apiKey)
			.orElseThrow(NotAuthenticatedException::new);

		if(deviceValidationInfo.archived() || deviceValidationInfo.deactivated()) {
			throw new DeviceArchivedException();
		}

		CharacteristicValidationInfo characteristicValidationInfo = this.findCharacteristicValidationPort.findCharacteristicValidation(deviceValidationInfo.deviceId(),
			characteristicId
		).orElseThrow(CharacteristicNotFoundException::new);

		if(characteristicValidationInfo.archived()) {
			throw new CharacteristicArchivedException();
		}

		return deviceValidationInfo.deviceId();
	}
}
