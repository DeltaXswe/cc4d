package it.deltax.produlytics.uibackend.admins.devices.business;

import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.UpdateDeviceNameUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedDevicePort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.UpdateDeviceNamePort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class UpdateDeviceNameService implements UpdateDeviceNameUseCase {
	private final FindDetailedDevicePort findDetailedDevicePort;
	private final UpdateDeviceNamePort updateDeviceNamePort;

	public UpdateDeviceNameService(FindDetailedDevicePort findDetailedDevicePort, UpdateDeviceNamePort updateDeviceNamePort) {
		this.findDetailedDevicePort = findDetailedDevicePort;
		this.updateDeviceNamePort = updateDeviceNamePort;
	}

	@Override
	public void updateDeviceName(TinyDevice command) throws BusinessException {
		DetailedDevice.DetailedDeviceBuilder toUpdate = findDetailedDevicePort.findDetailedDevice(command.id())
			.map(device -> device.toBuilder())
			.orElseThrow(() -> new BusinessException("deviceNotFound", ErrorType.NOT_FOUND));

		toUpdate.withName(command.name());
		updateDeviceNamePort.updateDeviceName(toUpdate.build());
	}
}
