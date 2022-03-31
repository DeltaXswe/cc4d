package it.deltax.produlytics.uibackend.admins.business;

import it.deltax.produlytics.uibackend.admins.business.ports.in.UpdateDeviceNameUseCase;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindTinyDevicePort;
import it.deltax.produlytics.uibackend.admins.business.ports.out.UpdateDeviceNamePort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class UpdateDeviceNameService implements UpdateDeviceNameUseCase {
	private final FindTinyDevicePort findTinyDevicePort;
	private final UpdateDeviceNamePort updateDeviceNamePort;

	public UpdateDeviceNameService(FindTinyDevicePort findTinyDevicePort, UpdateDeviceNamePort updateDeviceNamePort) {
		this.findTinyDevicePort = findTinyDevicePort;
		this.updateDeviceNamePort = updateDeviceNamePort;
	}

	@Override
	public void updateDeviceName(TinyDevice command) throws BusinessException {
		TinyDevice.TinyDeviceBuilder toUpdate = findTinyDevicePort.find(command.id())
			.map(device -> device.toBuilder())
			.orElseThrow(() -> new BusinessException("deviceNotFound", ErrorType.NOT_FOUND));

		updateDeviceNamePort.updateDeviceNamePort(command.id(), command.name());
	}
}
