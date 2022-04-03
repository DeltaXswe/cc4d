package it.deltax.produlytics.api.detections.business.domain.validate;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
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
	public CharacteristicId validateAndFindDeviceId(String apiKey, int characteristicId) throws BusinessException {

		DeviceInfo deviceInfo = this.findDeviceInfoByApiKeyPort.findDeviceByApiKey(apiKey)
			.orElseThrow(() -> new BusinessException("notAuthenticated", ErrorType.AUTHENTICATION));

		if(deviceInfo.archived() || deviceInfo.deactivated()) {
			throw new BusinessException("archived", ErrorType.ARCHIVED);
		}

		CharacteristicId characteristicIdComp = new CharacteristicId(deviceInfo.deviceId(), characteristicId);

		CharacteristicInfo characteristicInfo = this.findCharacteristicInfoPort.findCharacteristic(characteristicIdComp)
			.orElseThrow(() -> new BusinessException("characteristicNotFound", ErrorType.NOT_FOUND));

		if(characteristicInfo.archived()) {
			throw new BusinessException("archived", ErrorType.ARCHIVED);
		}

		return characteristicIdComp;
	}
}
