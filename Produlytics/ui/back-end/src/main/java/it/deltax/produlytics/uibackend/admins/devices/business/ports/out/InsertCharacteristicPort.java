package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;

public interface InsertCharacteristicPort {
	public int insertByDevice(int deviceId, NewCharacteristic characteristic);
}
