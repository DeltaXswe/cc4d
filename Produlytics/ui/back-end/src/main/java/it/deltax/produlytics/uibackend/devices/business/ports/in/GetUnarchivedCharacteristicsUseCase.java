package it.deltax.produlytics.uibackend.devices.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyCharacteristic;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import java.util.List;

/**
 * Interfaccia che rappresenta il caso d'uso di ottenimento delle caratteristiche non archiviate di
 * una macchina.
 */
public interface GetUnarchivedCharacteristicsUseCase {
  List<TinyCharacteristic> getByDevice(int deviceId) throws BusinessException;
}
