package it.deltax.produlytics.uibackend.admins.business;

import it.deltax.produlytics.uibackend.admins.business.ports.in.ModifyDeviceUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindUnarchivedDevicePort;
import it.deltax.produlytics.uibackend.admins.business.ports.out.ModifyDevicePort;
import org.springframework.stereotype.Service;

@Service
public class ModifyDeviceService implements ModifyDeviceUseCase {
	private final FindUnarchivedDevicePort findDevicePort;
	private final ModifyDevicePort modifyDeviceNamePort;

	public ModifyDeviceService(FindUnarchivedDevicePort findDevicePort, ModifyDevicePort modifyDeviceNamePort) {
		this.findDevicePort = findDevicePort;
		this.modifyDeviceNamePort = modifyDeviceNamePort;
	}

	@Override
	public boolean modifyDevice(int deviceId, String name){
		if(findDevicePort.find(deviceId).isPresent())
			return modifyDeviceNamePort.modifyDevicePort(deviceId, name) > 0;
		return false;
	}
}
