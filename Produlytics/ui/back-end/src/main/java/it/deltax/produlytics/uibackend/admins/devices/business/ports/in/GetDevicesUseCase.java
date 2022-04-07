package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.Device;

import java.util.List;

public interface GetDevicesUseCase {
	List<Device> getDevices();
}
