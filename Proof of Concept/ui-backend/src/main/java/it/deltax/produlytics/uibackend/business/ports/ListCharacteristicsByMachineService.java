package it.deltax.produlytics.uibackend.business.ports;

import it.deltax.produlytics.uibackend.business.domain.CharacteristicLight;
import it.deltax.produlytics.uibackend.business.ports.in.ListCharacteristicsByMachineUseCase;
import it.deltax.produlytics.uibackend.business.ports.out.ListCharacteristicsByMachinePort;

import java.util.List;

public class ListCharacteristicsByMachineService implements ListCharacteristicsByMachineUseCase {

    private final ListCharacteristicsByMachinePort port;

    public ListCharacteristicsByMachineService(ListCharacteristicsByMachinePort port) {
        this.port = port;
    }

    @Override
    public List<CharacteristicLight> listByMachine(long machineId) {
        return port.listByMachine(machineId);
    }
}

