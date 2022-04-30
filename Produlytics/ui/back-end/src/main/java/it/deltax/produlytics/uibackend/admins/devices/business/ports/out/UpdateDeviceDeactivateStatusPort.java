package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;

/** La porta per l'aggiornamento dello stato di attivazione di una macchina */
public interface UpdateDeviceDeactivateStatusPort {
  void updateDeviceDeactivateStatus(DetailedDevice device);
}
