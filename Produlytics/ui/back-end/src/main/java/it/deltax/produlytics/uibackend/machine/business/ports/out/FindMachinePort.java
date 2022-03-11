package it.deltax.produlytics.uibackend.machine.business.ports.out;

import it.deltax.produlytics.uibackend.machine.business.domain.MachineLight;

import java.util.Optional;

public interface FindMachinePort {
    Optional<MachineLight> find(int machineId);
}
