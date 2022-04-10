package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;

import java.util.Optional;

public interface FindDetailedDevicePort {
	Optional<DetailedDevice> findDetailedDevice(int deviceId);
}