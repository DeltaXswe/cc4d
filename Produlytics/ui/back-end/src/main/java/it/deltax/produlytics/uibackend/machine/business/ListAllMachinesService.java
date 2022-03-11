package it.deltax.produlytics.uibackend.machine.business;

import it.deltax.produlytics.uibackend.machine.business.domain.MachineLight;
import it.deltax.produlytics.uibackend.machine.business.ports.in.ListAllMachinesUseCase;
import it.deltax.produlytics.uibackend.machine.business.ports.out.ListAllMachinesPort;

import java.util.List;

public class ListAllMachinesService implements ListAllMachinesUseCase {

    private final ListAllMachinesPort port;

    public ListAllMachinesService(ListAllMachinesPort port) {
        this.port = port;
    }

    @Override
    public List<MachineLight> listAll() {
        return port.listAll();
    }
}
