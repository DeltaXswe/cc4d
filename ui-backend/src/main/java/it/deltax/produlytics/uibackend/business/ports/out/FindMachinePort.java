package it.deltax.produlytics.uibackend.business.ports.out;

import it.deltax.produlytics.uibackend.business.domain.MachineLight;

import java.util.Optional;

public interface FindMachinePort {
    Optional<MachineLight> find(long machine);
}
