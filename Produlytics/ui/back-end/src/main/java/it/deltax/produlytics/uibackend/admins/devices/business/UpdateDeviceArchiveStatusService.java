package it.deltax.produlytics.uibackend.admins.devices.business;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceArchiveStatus;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.UpdateDeviceArchiveStatusUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedDevicePort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.UpdateDeviceArchiveStatusPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class UpdateDeviceArchiveStatusService implements UpdateDeviceArchiveStatusUseCase {
	private final FindDetailedDevicePort findDetailedDevicePort;
	private final UpdateDeviceArchiveStatusPort updateDeviceArchiveStatus;

	/**
	 * Il costruttore
	 * @param findDetailedDevicePort la porta per trovare una macchina completa di tutte le sue informazioni
	 * @param updateDeviceArchiveStatus la porta per aggiornare lo stato di archiviazione di una macchina
	 */
	public UpdateDeviceArchiveStatusService(
		FindDetailedDevicePort findDetailedDevicePort,
		UpdateDeviceArchiveStatusPort updateDeviceArchiveStatus) {
		this.findDetailedDevicePort = findDetailedDevicePort;
		this.updateDeviceArchiveStatus = updateDeviceArchiveStatus;
	}

	/**
	 * Aggiorna lo stato di archiviazione di una macchina
	 * @param deviceArchiveStatus la macchina con lo stato di archiviazione aggiornato
	 * @throws BusinessException se la macchina non è stata trovata
	 */
	@Override
	public void updateDeviceArchiveStatus(DeviceArchiveStatus deviceArchiveStatus) throws BusinessException {
		DetailedDevice.DetailedDeviceBuilder toUpdate =
			this.findDetailedDevicePort.findDetailedDevice(deviceArchiveStatus.id())
			.map(device -> device.toBuilder())
			.orElseThrow(() -> new BusinessException("deviceNotFound", ErrorType.NOT_FOUND));

		toUpdate.withArchived(deviceArchiveStatus.archived());
		this.updateDeviceArchiveStatus.updateDeviceArchiveStatus(toUpdate.build());
	}
}

