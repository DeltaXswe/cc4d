package it.deltax.produlytics.uibackend.admins.devices.business.services;

import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.GetDevicesUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.Device;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.GetDevicesPort;

import java.util.List;

/**
 * Il service per l'ottenimento delle macchine
 */
public class GetDevicesService implements GetDevicesUseCase {
	private final GetDevicesPort getDevicesPort;

	/**
	 * Il costruttore
	 * @param getDevicesPort la porta per ottenere le macchine
	 */
	public GetDevicesService(GetDevicesPort getDevicesPort){
		this.getDevicesPort=getDevicesPort;
	}

	/**
	 * Restituisce le macchine
	 * @return la lista delle macchine
	 */
	@Override
	public List<Device> getDevices() {
		return this.getDevicesPort.getDevices();
	}
}
