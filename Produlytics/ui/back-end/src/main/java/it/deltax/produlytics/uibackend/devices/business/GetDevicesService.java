package it.deltax.produlytics.uibackend.devices.business;

import it.deltax.produlytics.uibackend.admins.business.ports.in.GetDevicesUseCase;
import it.deltax.produlytics.uibackend.devices.business.domain.Device;
import it.deltax.produlytics.uibackend.devices.business.ports.out.GetDevicesPort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetDevicesService implements GetDevicesUseCase {
	GetDevicesPort getDevicesPort;

	public GetDevicesService(GetDevicesPort getDevicesPort){
		this.getDevicesPort=getDevicesPort;
	}

	@Override
	public List<Device> getDevices() {
		return getDevicesPort.getDevices();
	}
}
