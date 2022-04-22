package it.deltax.produlytics.uibackend.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyCharacteristic;

import java.util.List;

/**
 * La porta per l'ottenimento delle caratteristiche non archiviate di una macchina
 */
public interface FindAllUnarchivedCharacteristicsPort {
    List<TinyCharacteristic> findAllByDeviceId(int deviceId);
}