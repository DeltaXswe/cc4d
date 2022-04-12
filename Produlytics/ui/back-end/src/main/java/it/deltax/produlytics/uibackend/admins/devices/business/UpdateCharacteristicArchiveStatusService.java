package it.deltax.produlytics.uibackend.admins.devices.business;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.CharacteristicArchiveStatus;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.UpdateCharacteristicArchiveStatusUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.UpdateCharacteristicPort;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.stereotype.Service;

/**
 * Il service per l'aggiornamento dello stato di archiviazione di una caratteristica
 * @author Alberto Lazari
 */
@Service
public class UpdateCharacteristicArchiveStatusService implements UpdateCharacteristicArchiveStatusUseCase {
	private final FindDetailedCharacteristicPort findDetailedCharacteristicPort;
	private final UpdateCharacteristicPort updateCharacteristicPort;

	/**
	 * Il costruttore
	 * @param findDetailedCharacteristicPort
	 * @param updateCharacteristicPort
	 */
	public UpdateCharacteristicArchiveStatusService(
		FindDetailedCharacteristicPort findDetailedCharacteristicPort,
		UpdateCharacteristicPort updateCharacteristicPort
	) {
		this.findDetailedCharacteristicPort = findDetailedCharacteristicPort;
		this.updateCharacteristicPort = updateCharacteristicPort;
	}

	/**
	 * TODO
	 * @param characteristic la
	 * @throws BusinessException
	 */
	@Override
	public void updateCharacteristicArchiveStatus(CharacteristicArchiveStatus characteristic) throws BusinessException {

	}
}
