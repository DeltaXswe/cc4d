package it.deltax.produlytics.uibackend.devices.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.UnarchivedDevice;

import java.util.List;

public interface GetUnarchivedDevicesUseCase {
    List<UnarchivedDevice> getAll();
}
