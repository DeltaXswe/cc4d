package it.deltax.produlytics.uibackend.admins.devices.business;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.CharacteristicArchiveStatus;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.UpdateCharacteristicArchiveStatusUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.UpdateCharacteristicPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.stereotype.Service;

/**
 * Il service per l'aggiornamento dello stato di archiviazione di una caratteristica
 */
@Service
public class UpdateCharacteristicArchiveStatusService implements UpdateCharacteristicArchiveStatusUseCase {
	private final FindDetailedCharacteristicPort findDetailedCharacteristicPort;
	private final UpdateCharacteristicPort updateCharacteristicPort;

	/**
	 * Il costruttore
	 * @param findDetailedCharacteristicPort la porta per trovare una caratteristica completa di tutte le sue
	 *                                       informazioni
	 * @param updateCharacteristicPort la porta per modificare le informazioni di una caratteristica
	 */
	public UpdateCharacteristicArchiveStatusService(
		FindDetailedCharacteristicPort findDetailedCharacteristicPort,
		UpdateCharacteristicPort updateCharacteristicPort
	) {
		this.findDetailedCharacteristicPort = findDetailedCharacteristicPort;
		this.updateCharacteristicPort = updateCharacteristicPort;
	}

	/**
	 * Aggiorna lo stato di archiviazione di una caratteristica
	 * @param toUpdate la caratteristica con lo stato di archiviazione aggiornato
	 * @throws BusinessException se la caratteristica è inesistente
	 */
	@Override
	public void updateCharacteristicArchiveStatus(CharacteristicArchiveStatus toUpdate) throws BusinessException {
		updateCharacteristicPort.updateCharacteristic(
			findDetailedCharacteristicPort.findByCharacteristic(toUpdate.deviceId(), toUpdate.id()).map(
				characteristic -> characteristic.toBuilder()
					.withArchived(toUpdate.archived())
					.build()
				)
				.orElseThrow(() -> new BusinessException("characteristicNotFound", ErrorType.NOT_FOUND))
		);
	}
}
