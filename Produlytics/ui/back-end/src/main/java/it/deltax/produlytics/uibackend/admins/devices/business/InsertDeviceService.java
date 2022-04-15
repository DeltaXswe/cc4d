package it.deltax.produlytics.uibackend.admins.devices.business;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.InsertCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.InsertDeviceUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceToInsert;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.InsertDevicePort;
import org.springframework.stereotype.Service;

/**
 * Il service per l'insierimento di una macchina
 */
@Service
public class InsertDeviceService implements InsertDeviceUseCase {
	private final InsertDevicePort insertDevicePort;
	private final CreateDevice createDevice;
	private final InsertCharacteristicPort insertCharacteristicPort;


	/**
	 * Il costruttore
	 * @param insertDevicePort la porta per inserire una macchina
	 * @param createDevice crea una nuova macchina
	 * @param insertCharacteristicPort la porta per inserire una caratteristica
	 */
	public InsertDeviceService(
		InsertDevicePort insertDevicePort,
		CreateDevice createDevice,
		InsertCharacteristicPort insertCharacteristicPort
	){
		this.insertDevicePort = insertDevicePort;
		this.createDevice = createDevice;
		this.insertCharacteristicPort = insertCharacteristicPort;
	}


	/**
	 * Inserisce una nuova macchina
	 * @param device la macchina da inserire
	 * @return l'id della macchina inserita
	 */
	@Override
	public int insertDevice(DeviceToInsert device) {
		int id = this.insertDevicePort.insertDevice(this.createDevice.createDevice(device.name()));
		for(NewCharacteristic characteristic : device.characteristics())
			this.insertCharacteristicPort.insertByDevice(id, characteristic);
		return id;
	}
}
