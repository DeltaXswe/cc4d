package it.deltax.produlytics.uibackend.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;

import java.util.Optional;

public interface FindTinyDevicePort {
    Optional<TinyDevice> find(int deviceId);
}
