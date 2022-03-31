package it.deltax.produlytics.uibackend.devices.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;

import java.util.List;

public interface GetUnarchivedDevicesUseCase {
    List<TinyDevice> getAll();
}
