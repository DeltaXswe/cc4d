package it.deltax.produlytics.uibackend.admins.devices.adapters;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.InsertCharacteristicPort;
import it.deltax.produlytics.uibackend.repositories.CharacteristicRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AdminCharacteristicAdapter implements FindCharacteristicPort, InsertCharacteristicPort {
	private final CharacteristicRepository repository;

	public AdminCharacteristicAdapter(CharacteristicRepository repository) {
		this.repository = repository;
	}

	@Override
	public List<CharacteristicEntity> findByName(String name) {
		return this.repository.findByName(name);
	}

	@Override
	public int insertByDevice(int deviceId, NewCharacteristic characteristic) {
		CharacteristicEntity entity = this.repository.saveAndFlush(new CharacteristicEntity(
			new CharacteristicEntityId(deviceId),
			characteristic.name(),
			characteristic.upperLimit().isPresent() ? characteristic.upperLimit().getAsDouble() : null,
			characteristic.lowerLimit().isPresent() ? characteristic.lowerLimit().getAsDouble() : null,
			characteristic.autoAdjust(),
			characteristic.sampleSize().isPresent() ? characteristic.sampleSize().getAsInt() : null,
			characteristic.archived()
		));
		return entity.getId().getId();
	}
}
