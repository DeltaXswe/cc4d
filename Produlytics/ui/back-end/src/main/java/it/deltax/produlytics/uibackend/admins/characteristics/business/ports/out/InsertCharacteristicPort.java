package it.deltax.produlytics.uibackend.admins.characteristics.business.ports.out;

import it.deltax.produlytics.uibackend.admins.characteristics.business.domain.NewCharacteristic;

public interface InsertCharacteristicPort {
	public int insertByDevice(int deviceId, NewCharacteristic characteristic);
}
