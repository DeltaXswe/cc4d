package it.deltax.produlytics.uibackend.admins.devices.business;

import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.GetDevicesUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.Device;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.GetDevicesPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetDevicesService implements GetDevicesUseCase {
	GetDevicesPort getDevicesPort;

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
