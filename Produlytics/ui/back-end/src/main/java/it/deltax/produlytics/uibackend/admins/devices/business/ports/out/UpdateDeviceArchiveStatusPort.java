package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;

/** La porta per l'aggiornamento dello stato di archiviazione di una macchina. */
public interface UpdateDeviceArchiveStatusPort {
  void updateDeviceArchiveStatus(DetailedDevice device);
}
