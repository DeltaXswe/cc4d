package it.deltax.produlytics.uibackend.admins.devices.business.services;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.CharacteristicArchiveStatus;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.UpdateCharacteristicArchiveStatusUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.UpdateCharacteristicPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import org.springframework.stereotype.Service;

/**
 * Il service per l'aggiornamento dello stato di archiviazione di una caratteristica
 */
@Service
public class UpdateCharacteristicArchiveStatusService implements UpdateCharacteristicArchiveStatusUseCase {
	private final FindDetailedCharacteristicPort findCharacteristicPort;
	private final UpdateCharacteristicPort updateCharacteristicPort;

	/**
	 * Il costruttore
	 * @param findCharacteristicPort la porta per trovare una caratteristica completa di tutte le sue
	 *                                       informazioni
	 * @param updateCharacteristicPort la porta per modificare le informazioni di una caratteristica
	 */
	public UpdateCharacteristicArchiveStatusService(
		FindDetailedCharacteristicPort findCharacteristicPort,
		UpdateCharacteristicPort updateCharacteristicPort
	) {
		this.findCharacteristicPort = findCharacteristicPort;
		this.updateCharacteristicPort = updateCharacteristicPort;
	}

	/**
	 * Modifica lo stato di archiviazione di una caratteristica
	 * @param toUpdate la caratteristica con lo stato di archiviazione aggiornato
	 * @throws BusinessException se la caratteristica è inesistente
	 */
	@Override
	public void updateCharacteristicArchiveStatus(CharacteristicArchiveStatus toUpdate) throws BusinessException {
		updateCharacteristicPort.updateCharacteristic(
			findCharacteristicPort.findByCharacteristic(toUpdate.deviceId(), toUpdate.id()).map(
				characteristic -> characteristic.toBuilder()
					.withArchived(toUpdate.archived())
					.build()
				)
				.orElseThrow(() -> new BusinessException("characteristicNotFound", ErrorType.NOT_FOUND))
		);
	}
}
