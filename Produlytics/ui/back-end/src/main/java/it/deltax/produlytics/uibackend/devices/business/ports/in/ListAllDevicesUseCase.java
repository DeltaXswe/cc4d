package it.deltax.produlytics.uibackend.devices.business.ports.in;

import it.deltax.produlytics.uibackend.devices.business.domain.MachineLight;

import java.util.List;

public interface ListAllDevicesUseCase {
    List<MachineLight> listAll();
}
