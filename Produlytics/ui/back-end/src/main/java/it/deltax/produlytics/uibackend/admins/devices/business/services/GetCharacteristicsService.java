package it.deltax.produlytics.uibackend.admins.devices.business.services;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.Characteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.GetCharacteristicsUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindAllCharacteristicsPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Il service per l'ottenimento delle caratteristiche di una macchina
 */
@Service
public class GetCharacteristicsService implements GetCharacteristicsUseCase {
	private final FindAllCharacteristicsPort findCharacteristicsPort;

	/**
	 * Il costruttore
	 * @param findCharacteristicsPort la porta per ottenere la lista delle caratteristiche
	 */
	public GetCharacteristicsService(FindAllCharacteristicsPort findCharacteristicsPort) {
		this.findCharacteristicsPort = findCharacteristicsPort;
	}

	/**
	 * Restituisce la lista delle caratteristiche della macchina
	 * @param deviceId l'id della macchina
	 * @return la lista delle caratteristiche
	 * @throws BusinessException se la macchina non viene trovata
	 */
	@Override
	public List<Characteristic> getByDevice(int deviceId) throws BusinessException {
		List<Characteristic> characteristics = findCharacteristicsPort.findAllByDeviceId(deviceId);
		if (characteristics.isEmpty())
			throw new BusinessException("deviceNotFound", ErrorType.NOT_FOUND);

		return characteristics;
	}
}
