package it.deltax.produlytics.uibackend.admins.business;

import it.deltax.produlytics.uibackend.admins.business.domain.DeviceDeactivateStatus;
import it.deltax.produlytics.uibackend.admins.business.ports.in.UpdateDeviceDeactivateStatusUseCase;
import it.deltax.produlytics.uibackend.admins.business.ports.out.UpdateDeviceDeactivateStatusPort;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindTinyDevicePort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class UpdateDeviceDeactivateStatusService implements UpdateDeviceDeactivateStatusUseCase {
	private final FindTinyDevicePort findDevicePort;
	private final UpdateDeviceDeactivateStatusPort updateDeviceDeactivateStatusPort;

	public UpdateDeviceDeactivateStatusService(
		FindTinyDevicePort findDevicePort,
		UpdateDeviceDeactivateStatusPort updateDeviceDeactivateStatusPort) {
		this.findDevicePort = findDevicePort;
		this.updateDeviceDeactivateStatusPort = updateDeviceDeactivateStatusPort;
	}

	@Override
	public void updateDeviceDeactivateStatus(DeviceDeactivateStatus command) throws BusinessException {
		TinyDevice.TinyDeviceBuilder toUpdate = findDevicePort.find(command.deviceId())
			.map(device -> device.toBuilder())
			.orElseThrow(() -> new BusinessException("deviceNotFound", ErrorType.NOT_FOUND));

		updateDeviceDeactivateStatusPort.updateDeviceDeactivateStatus(command.deviceId(), command.deactivated());
	}
}
