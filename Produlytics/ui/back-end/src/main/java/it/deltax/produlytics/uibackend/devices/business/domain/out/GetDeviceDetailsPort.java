package it.deltax.produlytics.uibackend.devices.business.domain.out;

import it.deltax.produlytics.uibackend.devices.business.domain.DetailedDevice;

import java.util.Optional;

public interface GetDeviceDetailsPort {
	Optional<DetailedDevice> getDeviceDetails(int deviceId);

}
