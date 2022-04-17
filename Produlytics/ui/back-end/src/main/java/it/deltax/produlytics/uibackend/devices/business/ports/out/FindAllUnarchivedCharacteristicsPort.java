package it.deltax.produlytics.uibackend.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyCharacteristic;

import java.util.List;

public interface FindAllUnarchivedCharacteristicsPort {
    List<TinyCharacteristic> findAllByDeviceId(int deviceId);
}