package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.Device;

import java.util.List;

/**
 * L'interfaccia che rappresenta il caso d'uso di ottenimento delle macchine
 */
public interface GetDevicesUseCase {
	List<Device> getDevices();
}
