package it.deltax.produlytics.uibackend.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.UnarchivedDeviceInfo;

import java.util.Optional;

public interface FindDevicePort {
    Optional<UnarchivedDeviceInfo> find(int machineId);
}
