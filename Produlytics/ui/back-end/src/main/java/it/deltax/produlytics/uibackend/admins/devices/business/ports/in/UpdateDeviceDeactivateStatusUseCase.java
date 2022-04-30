package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceDeactivateStatus;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

/**
 * Interfaccia che rappresenta il caso d'uso di aggiornamento dello stato di attivazione di una
 * macchina
 */
public interface UpdateDeviceDeactivateStatusUseCase {
  void updateDeviceDeactivateStatus(DeviceDeactivateStatus device) throws BusinessException;
}
