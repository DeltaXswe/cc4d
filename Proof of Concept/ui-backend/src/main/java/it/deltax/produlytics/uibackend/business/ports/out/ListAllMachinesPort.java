package it.deltax.produlytics.uibackend.business.ports.out;


import it.deltax.produlytics.uibackend.business.domain.MachineLight;

import java.util.List;

public interface ListAllMachinesPort {
    List<MachineLight> listAll();
}
