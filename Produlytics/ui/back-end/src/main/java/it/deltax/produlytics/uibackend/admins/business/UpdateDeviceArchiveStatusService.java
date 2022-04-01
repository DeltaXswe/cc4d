package it.deltax.produlytics.uibackend.admins.business;

import it.deltax.produlytics.uibackend.admins.business.domain.DeviceArchiveStatus;
import it.deltax.produlytics.uibackend.admins.business.ports.in.UpdateDeviceArchiveStatusUseCase;
import it.deltax.produlytics.uibackend.admins.business.ports.out.UpdateDeviceArchiveStatusPort;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindTinyDevicePort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class UpdateDeviceArchiveStatusService implements UpdateDeviceArchiveStatusUseCase {
	private final FindTinyDevicePort findDevicePort;
	private final UpdateDeviceArchiveStatusPort updateDeviceArchiveStatus;

	public UpdateDeviceArchiveStatusService(
		FindTinyDevicePort findDevicePort, UpdateDeviceArchiveStatusPort updateDeviceArchiveStatus) {
		this.findDevicePort = findDevicePort;
		this.updateDeviceArchiveStatus = updateDeviceArchiveStatus;
	}

	@Override
	public void modDevArchStatus(DeviceArchiveStatus command) throws BusinessException {
		TinyDevice.TinyDeviceBuilder toUpdate = findDevicePort.find(command.deviceId())
			.map(device -> device.toBuilder())
			.orElseThrow(() -> new BusinessException("deviceNotFound", ErrorType.NOT_FOUND));

		updateDeviceArchiveStatus.updateDeviceArchiveStatus(command.deviceId(), command.archived());
	}
}

