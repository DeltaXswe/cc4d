package it.deltax.uibackend.business.ports.out;

import it.deltax.uibackend.business.domain.MachineLight;

import java.util.Optional;

public interface FindMachinePort {
    Optional<MachineLight> find(long machine);
}
