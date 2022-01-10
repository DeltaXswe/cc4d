package it.deltax.uibackend.business.ports;

import it.deltax.uibackend.business.domain.MachineLight;
import it.deltax.uibackend.business.ports.in.ListAllMachinesUseCase;
import it.deltax.uibackend.business.ports.out.ListAllMachinesPort;

import java.util.List;

public class ListAllMachinesService implements ListAllMachinesUseCase {

    private final ListAllMachinesPort port;

    public ListAllMachinesService(ListAllMachinesPort port) {
        this.port = port;
    }

    @Override
    public List<MachineLight> listAllMachines() {
        return port.listAllMachines();
    }
}
