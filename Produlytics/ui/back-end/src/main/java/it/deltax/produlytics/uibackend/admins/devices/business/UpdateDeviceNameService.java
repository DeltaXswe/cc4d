package it.deltax.produlytics.uibackend.admins.devices.business;

import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.UpdateDeviceNameUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedDevicePort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.UpdateDeviceNamePort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import org.springframework.stereotype.Service;

/**
 * Il service per l'aggiornamento del nome di una macchina
 */
@Service
public class UpdateDeviceNameService implements UpdateDeviceNameUseCase {
	private final FindDetailedDevicePort findDetailedDevicePort;
	private final UpdateDeviceNamePort updateDeviceNamePort;

	/**
	 * Il costruttore
	 * @param findDetailedDevicePort la porta per trovare una macchina completa di tutte le sue informazioni
	 * @param updateDeviceNamePort la porta per aggiornare il nome di una macchina
	 */
	public UpdateDeviceNameService(
		FindDetailedDevicePort findDetailedDevicePort,
		UpdateDeviceNamePort updateDeviceNamePort) {
		this.findDetailedDevicePort = findDetailedDevicePort;
		this.updateDeviceNamePort = updateDeviceNamePort;
	}

	/**
	 * Aggiorna il nome di una macchina
	 * @param tinyDevice la macchina con il nome aggiornato
	 * @throws BusinessException se la macchina non è stata trovata
	 */
	@Override
	public void updateDeviceName(TinyDevice tinyDevice) throws BusinessException {
		DetailedDevice.DetailedDeviceBuilder toUpdate =
			this.findDetailedDevicePort.findDetailedDevice(tinyDevice.id())
			.map(device -> device.toBuilder())
			.orElseThrow(() -> new BusinessException("deviceNotFound", ErrorType.NOT_FOUND));

		toUpdate.withName(tinyDevice.name());
		this.updateDeviceNamePort.updateDeviceName(toUpdate.build());
	}
}
