package it.deltax.produlytics.uibackend.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.DeviceDetails;

import java.util.Optional;

public interface GetDeviceDetailsPort {
	Optional<DeviceDetails> getDeviceDetails(int deviceId);

}
