package it.deltax.produlytics.api.detections.business.domain.validate;

import it.deltax.produlytics.api.detections.business.ports.out.FindCharacteristicInfoPort;
import it.deltax.produlytics.api.detections.business.ports.out.FindDeviceInfoByApiKeyPort;
import it.deltax.produlytics.api.exceptions.BusinessException;
import it.deltax.produlytics.api.exceptions.ErrorType;

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
	public ValidationInfo validateAndFindDeviceId(String apiKey, int characteristicId) throws BusinessException {

		DeviceInfo deviceInfo = this.findDeviceInfoByApiKeyPort.findDeviceByApiKey(apiKey)
			.orElseThrow(() -> new BusinessException("notAuthenticated", ErrorType.AUTHENTICATION));

		if(deviceInfo.archived() || deviceInfo.deactivated()) {
			throw new BusinessException("archived", ErrorType.ARCHIVED);
		}

		CharacteristicInfo characteristicInfo = this.findCharacteristicInfoPort.findCharacteristic(deviceInfo.deviceId(),
				characteristicId
			)
			.orElseThrow(() -> new BusinessException("characteristicNotFound", ErrorType.NOT_FOUND));

		if(characteristicInfo.archived()) {
			throw new BusinessException("archived", ErrorType.ARCHIVED);
		}

		return new ValidationInfo(deviceInfo.deviceId());
	}
}
