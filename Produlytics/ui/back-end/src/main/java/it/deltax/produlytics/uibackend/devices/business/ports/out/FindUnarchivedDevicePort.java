package it.deltax.produlytics.uibackend.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.UnarchivedDevice;

import java.util.Optional;

public interface FindUnarchivedDevicePort {
    Optional<UnarchivedDevice> find(int deviceId);
}
