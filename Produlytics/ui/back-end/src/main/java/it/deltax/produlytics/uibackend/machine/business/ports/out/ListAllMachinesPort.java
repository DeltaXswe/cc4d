package it.deltax.produlytics.uibackend.machine.business.ports.out;


import it.deltax.produlytics.uibackend.machine.business.domain.MachineLight;

import java.util.List;

public interface ListAllMachinesPort {
    List<MachineLight> listAll();
}
