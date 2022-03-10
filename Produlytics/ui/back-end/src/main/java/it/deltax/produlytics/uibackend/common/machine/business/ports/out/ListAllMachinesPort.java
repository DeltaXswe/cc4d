package it.deltax.produlytics.uibackend.common.machine.business.ports.out;


import it.deltax.produlytics.uibackend.common.machine.business.domain.MachineLight;

import java.util.List;

public interface ListAllMachinesPort {
    List<MachineLight> listAll();
}
