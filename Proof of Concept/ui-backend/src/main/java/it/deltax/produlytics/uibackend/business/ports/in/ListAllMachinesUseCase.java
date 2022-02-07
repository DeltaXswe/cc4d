package it.deltax.produlytics.uibackend.business.ports.in;

import it.deltax.produlytics.uibackend.business.domain.MachineLight;

import java.util.List;

public interface ListAllMachinesUseCase {
    List<MachineLight> listAll();
}
