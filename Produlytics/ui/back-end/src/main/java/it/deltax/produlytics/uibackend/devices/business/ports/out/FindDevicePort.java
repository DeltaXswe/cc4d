package it.deltax.produlytics.uibackend.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.Device;

import java.util.Optional;

public interface FindDevicePort {
	Optional<Device> find(int deviceId);
}
