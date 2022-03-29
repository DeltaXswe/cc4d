package it.deltax.produlytics.uibackend.admins.business;

import it.deltax.produlytics.uibackend.admins.business.ports.in.ModDevArchStatusUseCase;
import it.deltax.produlytics.uibackend.admins.business.ports.out.ModDevArchStatusPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindUnarchivedDevicePort;
import org.springframework.stereotype.Service;

@Service
public class ModDevArchStatusService implements ModDevArchStatusUseCase {
	private final FindUnarchivedDevicePort findDevicePort;
	private final ModDevArchStatusPort modDevArchStatusPort;

	public ModDevArchStatusService(
		FindUnarchivedDevicePort findDevicePort, ModDevArchStatusPort modDevArchStatusPort) {
		this.findDevicePort = findDevicePort;
		this.modDevArchStatusPort = modDevArchStatusPort;
	}

	@Override
	public boolean modDevArchStatus(int deviceId, boolean archived) {
		if(findDevicePort.find(deviceId).isPresent()){
			return modDevArchStatusPort.modifyDeviceArchivedStatus(deviceId, archived) > 0;
		}
		return false;
	}
}
