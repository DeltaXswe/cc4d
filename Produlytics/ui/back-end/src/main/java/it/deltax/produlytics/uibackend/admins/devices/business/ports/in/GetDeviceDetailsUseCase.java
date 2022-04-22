package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;

/**
 * L'interfaccia che rappresenta il caso d'uso di ottenimento di una macchina dettagliata
 */
public interface GetDeviceDetailsUseCase {
	DetailedDevice getDeviceDetails(int id) throws BusinessException;
}
