package it.deltax.produlytics.uibackend.admins.devices.adapters;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.Characteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindAllCharacteristicsPort;
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
public class AdminCharacteristicAdapter implements
	FindCharacteristicPort,
	InsertCharacteristicPort,
	FindAllCharacteristicsPort
{
	private final CharacteristicRepository repo;

	/**
	 * Il costruttore
	 * @param repo lo strato di persistenza con i dati sulle caratteristiche
	 */
	public AdminCharacteristicAdapter(CharacteristicRepository repo) {
		this.repo = repo;
	}

	/**
	 * Restituisce tutte le caratteristiche di una macchina
	 * @param deviceId l'id della macchina
	 * @return la lista delle caratteristiche della macchina
	 */
	@Override
	public List<Characteristic> findAllByDeviceId(int deviceId) {
		return repo.findByDeviceId(deviceId).stream()
			.map(characteristic -> Characteristic.toBuilder()
				.withId(characteristic.getId())
				.withName(characteristic.getName())
				.withArchived(characteristic.getArchived())
				.build()
			)
			.toList();
	}

	/**
	 * Restituisce le caratteristiche di una macchina con un dato nome
	 * @param deviceId l'id della macchina
	 * @param name il nome delle caratteristiche
	 * @return lista delle caratteristiche trovate
	 */
	@Override
	public List<CharacteristicEntity> findByDeviceAndName(int deviceId, String name) {
		return this.repo.findByDeviceIdAndName(deviceId, name);
	}

	/**
	 * Inserisce una nuova caratteristica in una macchina
	 * @param deviceId l'id della macchina
	 * @param characteristic la caratteristica da inserire
	 * @return l'id della nuova caratteristica
	 */
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
