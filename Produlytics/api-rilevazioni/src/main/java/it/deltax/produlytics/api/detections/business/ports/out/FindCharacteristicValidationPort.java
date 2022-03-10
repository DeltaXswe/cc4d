package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.validate.CharacteristicValidationInfo;

import java.util.Optional;

public interface FindCharacteristicValidationPort {
	Optional<CharacteristicValidationInfo> findCharacteristicValidation(int deviceId, int characteristicId);
}
