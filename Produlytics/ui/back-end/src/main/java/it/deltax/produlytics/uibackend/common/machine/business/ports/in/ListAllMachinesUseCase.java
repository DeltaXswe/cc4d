package it.deltax.produlytics.uibackend.common.machine.business.ports.in;

import it.deltax.produlytics.uibackend.common.machine.business.domain.MachineLight;

import java.util.List;

public interface ListAllMachinesUseCase {
    List<MachineLight> listAll();
}
