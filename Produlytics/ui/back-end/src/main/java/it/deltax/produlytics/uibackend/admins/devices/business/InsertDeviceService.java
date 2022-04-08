package it.deltax.produlytics.uibackend.admins.devices.business;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.InsertCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.InsertDeviceUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceToInsert;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.InsertDevicePort;
import org.springframework.stereotype.Service;

@Service
public class InsertDeviceService implements InsertDeviceUseCase {
	private InsertDevicePort insertDevicePort;
	private CreateDevice createDevice;
	private InsertCharacteristicPort insertCharacteristicPort;

	public InsertDeviceService(InsertDevicePort insertDevicePort, CreateDevice createDevice){
		this.insertDevicePort = insertDevicePort;
		this.createDevice = createDevice;
	}

	@Override
	public int insertDevice(DeviceToInsert device) {
		int id = insertDevicePort.insertDevice(createDevice.createDevice(device.name()));
		for(NewCharacteristic characteristic : device.characteristics())
			insertCharacteristicPort.insertByDevice(id, characteristic);
		return id;
	}
}
