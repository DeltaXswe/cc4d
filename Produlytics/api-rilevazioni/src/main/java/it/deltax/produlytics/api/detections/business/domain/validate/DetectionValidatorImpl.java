package it.deltax.produlytics.api.detections.business.domain.validate;

import it.deltax.produlytics.api.detections.business.domain.DetectionInfo;
import it.deltax.produlytics.api.detections.business.domain.exception.CharacteristicArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.CharacteristicNotFoundException;
import it.deltax.produlytics.api.detections.business.domain.exception.DeviceArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.NotAuthenticatedException;
import it.deltax.produlytics.api.detections.business.ports.out.FindCharacteristicPort;
import it.deltax.produlytics.api.detections.business.ports.out.FindDeviceByApiKeyPort;

// Implementazione di riferimento di `DetectionValidator`.
public class DetectionValidatorImpl implements DetectionValidator {
	private final FindDeviceByApiKeyPort findDeviceByApiKeyPort;
	private final FindCharacteristicPort findCharacteristicPort;

	public DetectionValidatorImpl(
		FindDeviceByApiKeyPort findDeviceByApiKeyPort, FindCharacteristicPort findCharacteristicPort
	) {
		this.findDeviceByApiKeyPort = findDeviceByApiKeyPort;
		this.findCharacteristicPort = findCharacteristicPort;
	}

	@Override
	public DetectionInfo validateAndFindDeviceId(String apiKey, int characteristicId) throws
		CharacteristicNotFoundException,
		CharacteristicArchivedException,
		DeviceArchivedException,
		NotAuthenticatedException {

		DeviceInfo deviceInfo = this.findDeviceByApiKeyPort.findDeviceByApiKey(apiKey)
			.orElseThrow(NotAuthenticatedException::new);

		if(deviceInfo.archived() || deviceInfo.deactivated()) {
			throw new DeviceArchivedException();
		}

		CharacteristicInfo characteristicInfo = this.findCharacteristicPort.findCharacteristic(deviceInfo.deviceId(),
			characteristicId
		).orElseThrow(CharacteristicNotFoundException::new);

		if(characteristicInfo.archived()) {
			throw new CharacteristicArchivedException();
		}

		return new DetectionInfo(deviceInfo.deviceId(), characteristicInfo.limitsInfo());
	}
}
