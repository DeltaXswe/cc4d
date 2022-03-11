package it.deltax.produlytics.api.detections.business.ports.out;

import it.deltax.produlytics.api.detections.business.domain.validate.CharacteristicInfo;

import java.util.Optional;

public interface FindCharacteristicPort {
	Optional<CharacteristicInfo> findCharacteristicValidation(int deviceId, int characteristicId);
}
