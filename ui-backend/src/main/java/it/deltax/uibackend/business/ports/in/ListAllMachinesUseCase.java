package it.deltax.uibackend.business.ports.in;

import it.deltax.uibackend.business.domain.MachineLight;

import java.util.List;

public interface ListAllMachinesUseCase {
    List<MachineLight> listAllMachines();
}
