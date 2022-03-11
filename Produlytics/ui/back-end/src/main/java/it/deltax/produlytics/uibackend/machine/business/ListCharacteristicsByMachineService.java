package it.deltax.produlytics.uibackend.machine.business;

import it.deltax.produlytics.uibackend.machine.business.domain.CharacteristicLight;
import it.deltax.produlytics.uibackend.machine.business.ports.in.ListCharacteristicsByMachineUseCase;
import it.deltax.produlytics.uibackend.machine.business.ports.out.ListCharacteristicsByMachinePort;

import java.util.List;

public class ListCharacteristicsByMachineService implements ListCharacteristicsByMachineUseCase {

    private final ListCharacteristicsByMachinePort port;

    public ListCharacteristicsByMachineService(ListCharacteristicsByMachinePort port) {
        this.port = port;
    }

    @Override
    public List<CharacteristicLight> listByMachine(int machineId) {
        return port.listByMachine(machineId);
    }
}

