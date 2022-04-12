package it.deltax.produlytics.uibackend.admins.devices.adapters;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.Characteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindAllCharacteristicsPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.InsertCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.UpdateCharacteristicPort;
import it.deltax.produlytics.uibackend.repositories.CharacteristicRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;
import java.util.OptionalInt;

/**
 * L'adapter dello strato di persistenza per le operazioni svolte dagli amministratori sulle caratteristiche
 */
@Component
public class AdminCharacteristicAdapter implements FindDetailedCharacteristicPort,
	InsertCharacteristicPort,
	FindAllCharacteristicsPort,
	UpdateCharacteristicPort {
	private final CharacteristicRepository repo;

	/**
	 * Converte un oggetto <code>CharacteristicEntity</code> in uno <code>DetailedCharacteristic</code>
	 * @param characteristic la <code>CharacteristicEntity</code> da convertire
	 * @return la <code>DetailedCharacteristic</code> equivalente
	 */
	public static DetailedCharacteristic toDetailed(CharacteristicEntity characteristic) {
		return DetailedCharacteristic.builder()
			.withId(characteristic.getId())
			.withDeviceId(characteristic.getDeviceId())
			.withName(characteristic.getName())
			.withLowerLimit(
				characteristic.getLowerLimit() != null
				? OptionalDouble.of(characteristic.getLowerLimit())
				: OptionalDouble.empty()
			)
			.withUpperLimit(
				characteristic.getUpperLimit() != null
				? OptionalDouble.of(characteristic.getSampleSize())
				: OptionalDouble.empty()
			)
			.withAutoAdjust(characteristic.getAutoAdjust())
			.withSampleSize(
				characteristic.getSampleSize() != null
				? OptionalInt.of(characteristic.getSampleSize())
				: OptionalInt.empty()
			)
			.withArchived(characteristic.getArchived())
			.build();
	}

	/**
	 * Converte un oggetto <code>DetailedCharacteristic</code> in uno <code>CharacteristicEntity</code>
	 * @param characteristic la <code>DetailedCharacteristic</code> da convertire
	 * @return la <code>CharacteristicEntity</code> equivalente
	 */
	public static CharacteristicEntity toEntity(DetailedCharacteristic characteristic) {
		return new CharacteristicEntity(
			characteristic.id(),
			characteristic.deviceId(),
			characteristic.name(),
			characteristic.upperLimit().isPresent() ? characteristic.upperLimit().getAsDouble() : null,
			characteristic.lowerLimit().isPresent() ? characteristic.lowerLimit().getAsDouble() : null,
			characteristic.autoAdjust(),
			characteristic.sampleSize().isPresent() ? characteristic.sampleSize().getAsInt() : null,
			characteristic.archived()
		);
	}

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
			.map(characteristic -> Characteristic.builder()
				.withId(characteristic.getId())
				.withName(characteristic.getName())
				.withArchived(characteristic.getArchived())
				.build()
			)
			.toList();
	}

	/**
	 * Restituisce tutti i dettagli della caratteristica di una macchina
	 * @param deviceId l'id della macchina
	 * @param characteristicId l'id della caratteristica
	 * @return la caratteristica trovata
	 */
	@Override
	public Optional<DetailedCharacteristic> findByCharacteristic(int deviceId, int characteristicId) {
		return this.repo.findById(new CharacteristicEntityId(characteristicId, deviceId))
			.map(AdminCharacteristicAdapter::toDetailed);
	}

	/**
	 * Restituisce le caratteristiche di una macchina con un dato nome
	 * @param deviceId l'id della macchina
	 * @param name il nome delle caratteristiche
	 * @return la lista delle caratteristiche trovate
	 */
	@Override
	public List<DetailedCharacteristic> findByDeviceAndName(int deviceId, String name) {
		return this.repo.findByDeviceIdAndName(deviceId, name).stream()
			.map(AdminCharacteristicAdapter::toDetailed)
			.toList();
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

	/**
	 * Modifica le informazioni di una caratteristica
	 * @param characteristic la caratteristica da modificare
	 */
	@Override
	public void updateCharacteristic(DetailedCharacteristic characteristic) {
		this.repo.save(toEntity(characteristic));
	}
}
