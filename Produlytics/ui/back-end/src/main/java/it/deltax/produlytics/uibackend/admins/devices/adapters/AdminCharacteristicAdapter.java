package it.deltax.produlytics.uibackend.admins.devices.adapters;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.InsertCharacteristicPort;
import it.deltax.produlytics.uibackend.repositories.CharacteristicRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * L'adapter dello strato di persistenza per le operazioni svolte dagli amministratori sulle caratteristiche
 * @author Alberto Lazari
 */
@Component
public class AdminCharacteristicAdapter implements FindCharacteristicPort, InsertCharacteristicPort {
	private final CharacteristicRepository repo;

	/**
	 * Il costruttore
	 * @param repo lo strato di persistenza con i dati sulle caratteristiche
	 */
	public AdminCharacteristicAdapter(CharacteristicRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<CharacteristicEntity> findByName(String name) {
		return this.repo.findByName(name);
	}

	@Override
	public int insertByDevice(int deviceId, NewCharacteristic characteristic) {
		CharacteristicEntity entity = this.repo.saveAndFlush(new CharacteristicEntity(
			deviceId,
			characteristic.name(),
			characteristic.upperLimit().isPresent() ? characteristic.upperLimit().getAsDouble() : null,
			characteristic.lowerLimit().isPresent() ? characteristic.lowerLimit().getAsDouble() : null,
			characteristic.autoAdjust(),
			characteristic.sampleSize().isPresent() ? characteristic.sampleSize().getAsInt() : null,
			characteristic.archived()
		));
		return entity.getId();
	}
}
