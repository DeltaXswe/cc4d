package it.deltax.produlytics.uibackend.admins.business;

import it.deltax.produlytics.uibackend.devices.business.domain.DeviceArchiveStatus;
import it.deltax.produlytics.uibackend.admins.business.ports.in.UpdateDeviceArchiveStatusUseCase;
import it.deltax.produlytics.uibackend.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindDetailedDevicePort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.UpdateDeviceArchiveStatusPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class UpdateDeviceArchiveStatusService implements UpdateDeviceArchiveStatusUseCase {
	private final FindDetailedDevicePort findDetailedDevicePort;
	private final UpdateDeviceArchiveStatusPort updateDeviceArchiveStatus;

	public UpdateDeviceArchiveStatusService(
		FindDetailedDevicePort findDetailedDevicePort,
		UpdateDeviceArchiveStatusPort updateDeviceArchiveStatus) {
		this.findDetailedDevicePort = findDetailedDevicePort;
		this.updateDeviceArchiveStatus = updateDeviceArchiveStatus;
	}

	@Override
	public void updateDeviceArchiveStatus(DeviceArchiveStatus command) throws BusinessException {
		DetailedDevice.DetailedDeviceBuilder toUpdate = findDetailedDevicePort.findDetailedDevice(command.id())
			.map(device -> device.toBuilder())
			.orElseThrow(() -> new BusinessException("deviceNotFound", ErrorType.NOT_FOUND));

		toUpdate.withArchived(command.archived());
		updateDeviceArchiveStatus.updateDeviceArchiveStatus(toUpdate.build());
	}
}
