package it.deltax.produlytics.uibackend.admins.devices.business.ports.in;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.Characteristic;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import java.util.List;

/** Interfaccia che rappresenta il caso d'uso di ottenimento di una caratteristica. */
public interface GetCharacteristicsUseCase {
  List<Characteristic> getByDevice(int deviceId) throws BusinessException;
}
