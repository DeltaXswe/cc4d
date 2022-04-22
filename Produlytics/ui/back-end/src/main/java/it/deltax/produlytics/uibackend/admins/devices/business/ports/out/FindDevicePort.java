package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.Device;

import java.util.Optional;

/**
 * La porta per l'ottenimento di una macchina, dato il suo identificativo
 */
public interface FindDevicePort {
	Optional<Device> find(int deviceId);
}
