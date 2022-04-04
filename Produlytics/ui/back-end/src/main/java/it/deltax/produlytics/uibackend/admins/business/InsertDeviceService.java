package it.deltax.produlytics.uibackend.admins.business;

import it.deltax.produlytics.uibackend.admins.business.ports.in.InsertDeviceUseCase;
import it.deltax.produlytics.uibackend.devices.business.domain.DeviceToInsert;
import it.deltax.produlytics.uibackend.devices.business.ports.out.InsertDevicePort;
import org.springframework.stereotype.Service;

@Service
public class InsertDeviceService implements InsertDeviceUseCase {
	private InsertDevicePort insertDevicePort;
	private CreateDevice createDevice;
	//TODO InsertCharacteristicPort

	public InsertDeviceService(InsertDevicePort insertDevicePort, CreateDevice createDevice){
		this.insertDevicePort = insertDevicePort;
		this.createDevice = createDevice;
	}

	@Override
	public int insertDevice(DeviceToInsert device) {
		int id = insertDevicePort.insertDevice(createDevice.createDevice(device.name()));

		return id;
	}
}
