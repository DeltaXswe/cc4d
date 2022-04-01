package it.deltax.produlytics.uibackend.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.Device;

import java.util.List;

public interface GetDevicesPort {
	List<Device> getDevices();
}
