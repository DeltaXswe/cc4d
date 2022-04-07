package it.deltax.produlytics.uibackend.admins.devices.business;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.InsertCharacteristicUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.InsertCharacteristicPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindDetailedDevicePort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.stereotype.Service;

@Service
public class InsertCharacteristicService implements InsertCharacteristicUseCase {
	private final InsertCharacteristicPort insertPort;
	private final FindDetailedDevicePort findDevicePort;
	private final FindCharacteristicPort findCharacteristicPort;

	InsertCharacteristicService(
		InsertCharacteristicPort insertPort,
		FindDetailedDevicePort findDevicePort,
		FindCharacteristicPort findCharacteristicPort
	) {
		this.insertPort = insertPort;
		this.findDevicePort = findDevicePort;
		this.findCharacteristicPort = findCharacteristicPort;
	}

	public int insertByDevice(int deviceId, NewCharacteristic characteristic) throws BusinessException {
		if (findDevicePort.findDetailedDevice(deviceId).isEmpty())
			throw new BusinessException("deviceNotFound", ErrorType.NOT_FOUND);

		if (!findCharacteristicPort.findByName(characteristic.name()).isEmpty())
			throw new BusinessException("duplicateCharacteristicName", ErrorType.GENERIC);

		if (characteristic.autoAdjust() == false && (
			characteristic.upperLimit().isEmpty() || characteristic.lowerLimit().isEmpty()
		))
			throw new BusinessException("invalidValues", ErrorType.GENERIC);

		return insertPort.insertByDevice(deviceId, characteristic);
	}
}
