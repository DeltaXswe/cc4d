package it.deltax.uibackend.business.ports.out;


import it.deltax.uibackend.business.domain.MachineLight;

import java.util.List;

public interface ListAllMachinesPort {
    List<MachineLight> listAllMachines();
}
