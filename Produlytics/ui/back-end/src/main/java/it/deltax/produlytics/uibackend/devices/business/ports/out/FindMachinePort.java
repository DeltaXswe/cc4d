package it.deltax.produlytics.uibackend.devices.business.ports.out;

import it.deltax.produlytics.uibackend.devices.business.domain.MachineLight;

import java.util.Optional;

public interface FindMachinePort {
    Optional<MachineLight> find(int machineId);
}
