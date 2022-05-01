package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewDevice;

/** La porta per l'inserimento di una macchina. */
public interface InsertDevicePort {
  int insertDevice(NewDevice device);
}
