package it.deltax.produlytics.uibackend.admins.devices.business.services;

import it.deltax.produlytics.uibackend.admins.devices.business.CreateDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.InsertCharacteristicUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.InsertDeviceUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceToInsert;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindTinyDeviceByNamePort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.InsertDevicePort;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import org.springframework.stereotype.Service;

/**
 * Il service per l'insierimento di una macchina
 */
@Service
public class InsertDeviceService implements InsertDeviceUseCase {
	private final FindTinyDeviceByNamePort findTinyDeviceByNamePort;
	private final InsertDevicePort insertDevicePort;
	private final CreateDevice createDevice;
	private final InsertCharacteristicUseCase insertCharacteristicUseCase;


	/**
	 * Il costruttore
	 * @param insertDevicePort la porta per inserire una macchina
	 * @param createDevice crea una nuova macchina
	 * @param insertCharacteristicUseCase la porta per inserire una caratteristica
	 */
	public InsertDeviceService(
		FindTinyDeviceByNamePort findTinyDeviceByNamePort,
		InsertDevicePort insertDevicePort,
		CreateDevice createDevice,
		InsertCharacteristicUseCase insertCharacteristicUseCase
	){
		this.findTinyDeviceByNamePort = findTinyDeviceByNamePort;
		this.insertDevicePort = insertDevicePort;
		this.createDevice = createDevice;
		this.insertCharacteristicUseCase = insertCharacteristicUseCase;
	}

	/**
	 * Inserisce una nuova macchina
	 * @param device la macchina da inserire
	 * @return l'id della macchina inserita
	 * @throws BusinessException se esiste già una macchina con lo stesso nome
	 */
	@Override
	public int insertDevice(DeviceToInsert device) throws BusinessException {
		if(findTinyDeviceByNamePort.findByName(device.name()).isPresent()) {
			throw new BusinessException("duplicateDeviceName", ErrorType.GENERIC);
		} else {
			int id = this.insertDevicePort.insertDevice(this.createDevice.createDevice(device.name()));
			for(NewCharacteristic characteristic : device.characteristics())
				this.insertCharacteristicUseCase.insertByDevice(id, characteristic);
			return id;
		}
	}
}
