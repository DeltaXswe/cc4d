package it.deltax.produlytics.api.detections.adapters;

import it.deltax.produlytics.api.detections.business.domain.validate.CharacteristicValidationInfo;
import it.deltax.produlytics.api.detections.business.domain.validate.DeviceValidationInfo;
import it.deltax.produlytics.api.detections.business.ports.out.FindCharacteristicValidationPort;
import it.deltax.produlytics.api.detections.business.ports.out.FindDeviceValidationByApiKeyPort;
import it.deltax.produlytics.api.repositories.CharacteristicRepository;
import it.deltax.produlytics.api.repositories.DeviceRepository;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ValidationAdapter implements FindDeviceValidationByApiKeyPort, FindCharacteristicValidationPort {
	@Autowired
	private CharacteristicRepository characteristicRepository;
	@Autowired
	private DeviceRepository deviceRepository;

	@Override
	public Optional<DeviceValidationInfo> findDeviceByApiKey(
		String apiKey
	) {
		return this.deviceRepository.findByApiKey(apiKey)
			.map(deviceEntity -> new DeviceValidationInfo(deviceEntity.getId(),
				deviceEntity.getArchived(),
				deviceEntity.getDeactivated()
			));
	}

	@Override
	public Optional<CharacteristicValidationInfo> findCharacteristicValidation(
		int deviceId, int characteristicId
	) {
		return this.characteristicRepository.findById(new CharacteristicEntityId(deviceId, characteristicId)).map(
			characteristicEntity -> new CharacteristicValidationInfo(characteristicEntity.getArchived()));
	}
}
