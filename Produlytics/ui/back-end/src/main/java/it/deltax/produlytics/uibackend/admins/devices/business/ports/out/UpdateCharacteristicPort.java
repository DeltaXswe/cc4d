package it.deltax.produlytics.uibackend.admins.devices.business.ports.out;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedCharacteristic;

/** La porta per l'aggiornamento di una caratteristica. */
public interface UpdateCharacteristicPort {
  void updateCharacteristic(DetailedCharacteristic characteristic);
}
