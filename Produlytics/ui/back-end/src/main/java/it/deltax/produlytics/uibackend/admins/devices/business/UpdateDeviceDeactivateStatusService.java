package it.deltax.produlytics.uibackend.admins.devices.business;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceDeactivateStatus;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.UpdateDeviceDeactivateStatusUseCase;
import it.deltax.produlytics.uibackend.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedDevicePort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.UpdateDeviceDeactivateStatusPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class UpdateDeviceDeactivateStatusService implements UpdateDeviceDeactivateStatusUseCase {
	private final FindDetailedDevicePort findDetailedDevicePort;
	private final UpdateDeviceDeactivateStatusPort updateDeviceDeactivateStatusPort;

	public UpdateDeviceDeactivateStatusService(
		FindDetailedDevicePort findDetailedDevicePort,
		UpdateDeviceDeactivateStatusPort updateDeviceDeactivateStatusPort) {
		this.findDetailedDevicePort = findDetailedDevicePort;
		this.updateDeviceDeactivateStatusPort = updateDeviceDeactivateStatusPort;
	}

	@Override
	public void updateDeviceDeactivateStatus(DeviceDeactivateStatus command) throws BusinessException {
		DetailedDevice.DetailedDeviceBuilder toUpdate = findDetailedDevicePort.findDetailedDevice(command.id())
			.map(device -> device.toBuilder())
			.orElseThrow(() -> new BusinessException("deviceNotFound", ErrorType.NOT_FOUND));

		toUpdate.withDeactivated(command.deactivated());
		updateDeviceDeactivateStatusPort.updateDeviceDeactivateStatus(toUpdate.build());
	}
}
