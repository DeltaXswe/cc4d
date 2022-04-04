package it.deltax.produlytics.api.detections.business.domain.validate;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.ports.out.FindCharacteristicPort;
import it.deltax.produlytics.api.detections.business.ports.out.FindDeviceByApiKeyPort;
import it.deltax.produlytics.api.exceptions.BusinessException;
import it.deltax.produlytics.api.exceptions.ErrorType;

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
	public CharacteristicId validateAndFindId(String apiKey, int characteristicId) throws BusinessException {

		DeviceInfo deviceInfo = this.findDeviceByApiKeyPort.findDeviceByApiKey(apiKey)
			.orElseThrow(() -> new BusinessException("notAuthenticated", ErrorType.AUTHENTICATION));

		if(deviceInfo.archived() || deviceInfo.deactivated()) {
			throw new BusinessException("archived", ErrorType.ARCHIVED);
		}

		CharacteristicId characteristicIdComp = new CharacteristicId(deviceInfo.deviceId(), characteristicId);

		CharacteristicInfo characteristicInfo = this.findCharacteristicPort.findCharacteristic(characteristicIdComp)
			.orElseThrow(() -> new BusinessException("characteristicNotFound", ErrorType.NOT_FOUND));

		if(characteristicInfo.archived()) {
			throw new BusinessException("archived", ErrorType.ARCHIVED);
		}

		return characteristicIdComp;
	}
}
