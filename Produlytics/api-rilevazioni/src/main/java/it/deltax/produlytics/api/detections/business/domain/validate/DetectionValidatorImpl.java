package it.deltax.produlytics.api.detections.business.domain.validate;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.ports.out.FindCharacteristicByNamePort;
import it.deltax.produlytics.api.detections.business.ports.out.FindDeviceByApiKeyPort;
import it.deltax.produlytics.api.exceptions.BusinessException;
import it.deltax.produlytics.api.exceptions.ErrorType;

// Implementazione di riferimento di `DetectionValidator`.
public class DetectionValidatorImpl implements DetectionValidator {
	private final FindDeviceByApiKeyPort findDeviceByApiKeyPort;
	private final FindCharacteristicByNamePort findCharacteristicByNamePort;

	public DetectionValidatorImpl(
		FindDeviceByApiKeyPort findDeviceByApiKeyPort, FindCharacteristicByNamePort findCharacteristicByNamePort
	) {
		this.findDeviceByApiKeyPort = findDeviceByApiKeyPort;
		this.findCharacteristicByNamePort = findCharacteristicByNamePort;
	}

	@Override
	public CharacteristicId validateAndFindId(String apiKey, String characteristicName) throws BusinessException {

		DeviceInfo deviceInfo = this.findDeviceByApiKeyPort.findDeviceByApiKey(apiKey)
			.orElseThrow(() -> new BusinessException("notAuthenticated" , ErrorType.AUTHENTICATION));

		if(deviceInfo.archived() || deviceInfo.deactivated()) {
			throw new BusinessException("archived" , ErrorType.ARCHIVED);
		}

		CharacteristicInfo characteristicInfo = this.findCharacteristicByNamePort.findCharacteristicByName(deviceInfo.deviceId(),
				characteristicName
			)
			.orElseThrow(() -> new BusinessException("characteristicNotFound" , ErrorType.NOT_FOUND));

		if(characteristicInfo.archived()) {
			throw new BusinessException("archived" , ErrorType.ARCHIVED);
		}

		return new CharacteristicId(deviceInfo.deviceId(), characteristicInfo.characteristicId());
	}
}
