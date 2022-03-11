package it.deltax.produlytics.uibackend.machine.business.ports.in;

import it.deltax.produlytics.uibackend.machine.business.domain.MachineLight;

import java.util.List;

public interface ListAllMachinesUseCase {
    List<MachineLight> listAll();
}
