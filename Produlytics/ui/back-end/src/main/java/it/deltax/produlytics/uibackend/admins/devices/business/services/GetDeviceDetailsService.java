package it.deltax.produlytics.uibackend.admins.devices.business.services;

import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.GetDeviceDetailsUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedDevicePort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import org.springframework.stereotype.Service;

/**
 * Il service per l'ottenimento dei dettagli di una macchina
 */
@Service
public class GetDeviceDetailsService implements GetDeviceDetailsUseCase {
	private final FindDetailedDevicePort findDetailedDevicePort;

	/**
	 * Il costruttore
	 * @param findDetailedDevicePort la porta per trovare una macchina completa di tutte le sue informazioni
	 */
	public GetDeviceDetailsService(
		FindDetailedDevicePort findDetailedDevicePort
	) {
		this.findDetailedDevicePort = findDetailedDevicePort;
	}

	/**
	 * Restituisce la macchina con l'id dato
	 * @param id l'id della macchina da trovare
	 * @return la macchina con l'id data, con tutte le sue informazioni
	 * @throws BusinessException se la macchina con questo id non è stata trovata
	 */
	@Override
	public DetailedDevice getDeviceDetails(int id) throws BusinessException {
		DetailedDevice.DetailedDeviceBuilder result = this.findDetailedDevicePort.findDetailedDevice(id)
			.map(device -> device.toBuilder())
			.orElseThrow(() -> new BusinessException("deviceNotFound", ErrorType.NOT_FOUND));

		return result.build();
	}
}
