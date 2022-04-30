package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;

/** La porta per l'inserimento di una caratteristica in una macchina */
public interface InsertCharacteristicPort {
  int insertByDevice(int deviceId, NewCharacteristic characteristic);
}
