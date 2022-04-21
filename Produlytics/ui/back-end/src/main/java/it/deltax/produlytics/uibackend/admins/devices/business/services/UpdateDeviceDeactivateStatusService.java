package it.deltax.produlytics.uibackend.admins.devices.business.services;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceDeactivateStatus;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.UpdateDeviceDeactivateStatusUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedDevicePort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.UpdateDeviceDeactivateStatusPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

/**
 * Il service per l'aggiornamento dello stato di attivazione di una macchina
 */
public class UpdateDeviceDeactivateStatusService implements UpdateDeviceDeactivateStatusUseCase {
	private final FindDetailedDevicePort findDetailedDevicePort;
	private final UpdateDeviceDeactivateStatusPort updateDeviceDeactivateStatusPort;

	/**
	 * Il costruttore
	 * @param findDetailedDevicePort la porta per trovare una macchina completa di tutte le sue informazioni
	 * @param updateDeviceDeactivateStatusPort la porta per aggiornare lo stato di attivazione di una macchina
	 */
	public UpdateDeviceDeactivateStatusService(
		FindDetailedDevicePort findDetailedDevicePort,
		UpdateDeviceDeactivateStatusPort updateDeviceDeactivateStatusPort) {
		this.findDetailedDevicePort = findDetailedDevicePort;
		this.updateDeviceDeactivateStatusPort = updateDeviceDeactivateStatusPort;
	}

	/**
	 * Aggiorna lo stato di attivazione di una macchina
	 * @param deviceDeactivateStatus la macchina con lo stato di attivazione aggiornato
	 * @throws BusinessException se la macchina non è stata trovata
	 */
	@Override
	public void updateDeviceDeactivateStatus(DeviceDeactivateStatus deviceDeactivateStatus) throws BusinessException {
		DetailedDevice.DetailedDeviceBuilder toUpdate =
			this.findDetailedDevicePort.findDetailedDevice(deviceDeactivateStatus.id())
			.map(device -> device.toBuilder())
			.orElseThrow(() -> new BusinessException("deviceNotFound", ErrorType.NOT_FOUND));

		toUpdate.withDeactivated(deviceDeactivateStatus.deactivated());
		this.updateDeviceDeactivateStatusPort.updateDeviceDeactivateStatus(toUpdate.build());
	}
}
