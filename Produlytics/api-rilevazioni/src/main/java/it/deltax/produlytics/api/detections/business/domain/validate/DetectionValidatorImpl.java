package it.deltax.produlytics.api.detections.business.domain.validate;

import it.deltax.produlytics.api.detections.business.domain.exception.ArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.NotAuthenticatedException;
import it.deltax.produlytics.api.detections.business.domain.exception.NotFoundException;
import it.deltax.produlytics.api.detections.business.ports.out.FindCharacteristicInfoPort;
import it.deltax.produlytics.api.detections.business.ports.out.FindDeviceInfoByApiKeyPort;

// Implementazione di riferimento di `DetectionValidator`.
public class DetectionValidatorImpl implements DetectionValidator {
	private final FindDeviceInfoByApiKeyPort findDeviceInfoByApiKeyPort;
	private final FindCharacteristicInfoPort findCharacteristicInfoPort;

	public DetectionValidatorImpl(
		FindDeviceInfoByApiKeyPort findDeviceInfoByApiKeyPort, FindCharacteristicInfoPort findCharacteristicInfoPort
	) {
		this.findDeviceInfoByApiKeyPort = findDeviceInfoByApiKeyPort;
		this.findCharacteristicInfoPort = findCharacteristicInfoPort;
	}

	@Override
	public ValidationInfo validateAndFindDeviceId(String apiKey, int characteristicId)
	throws NotFoundException, ArchivedException, NotAuthenticatedException {

		DeviceInfo deviceInfo = this.findDeviceInfoByApiKeyPort.findDeviceByApiKey(apiKey)
			.orElseThrow(NotAuthenticatedException::new);

		if(deviceInfo.archived() || deviceInfo.deactivated()) {
			throw new ArchivedException();
		}

		CharacteristicInfo characteristicInfo = this.findCharacteristicInfoPort.findCharacteristic(deviceInfo.deviceId(),
			characteristicId
		).orElseThrow(NotFoundException::new);

		if(characteristicInfo.archived()) {
			throw new ArchivedException();
		}

		return new ValidationInfo(deviceInfo.deviceId());
	}
}
