package it.deltax.produlytics.uibackend.admins.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.Device;

import java.util.List;

public interface GetDevicesUseCase {
	List<Device> getDevices();
}
