package it.deltax.produlytics.api.detections.business.domain.validate;

import it.deltax.produlytics.api.detections.business.domain.exception.CharacteristicArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.CharacteristicNotFoundException;
import it.deltax.produlytics.api.detections.business.domain.exception.DeviceArchivedException;
import it.deltax.produlytics.api.detections.business.domain.exception.NotAuthenticatedException;
import it.deltax.produlytics.api.detections.business.ports.out.FindValidationInfoPort;

public class DetectionValidatorImpl implements DetectionValidator {
	private final FindValidationInfoPort findCharacteristicInfoPort;

	public DetectionValidatorImpl(FindValidationInfoPort findCharacteristicInfoPort) {
		this.findCharacteristicInfoPort = findCharacteristicInfoPort;
	}

	@Override
	public int validateAndFindDeviceId(String apiKey, int characteristicId) throws
		CharacteristicNotFoundException,
		CharacteristicArchivedException,
		DeviceArchivedException,
		NotAuthenticatedException {

		// TODO: Le Exception sono sbagliate
		final ValidationInfo characteristicInfo = findCharacteristicInfoPort.findValidationInfo(apiKey,
			characteristicId
		).orElseThrow(CharacteristicNotFoundException::new);
		if(!characteristicInfo.apiKey().equals(apiKey)) {
			throw new NotAuthenticatedException();
		}
		if(characteristicInfo.characteristicArchived()) {
			throw new CharacteristicArchivedException();
		}
		if(characteristicInfo.deviceArchived()) {
			throw new DeviceArchivedException();
		}
		if(characteristicInfo.deviceDeactivated()) {
			throw new DeviceArchivedException();
		}
		throw new RuntimeException();
	}
}
