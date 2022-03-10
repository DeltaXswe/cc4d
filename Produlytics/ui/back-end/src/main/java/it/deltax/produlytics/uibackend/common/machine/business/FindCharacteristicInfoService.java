package it.deltax.produlytics.uibackend.common.machine.business;

import it.deltax.produlytics.uibackend.common.machine.business.domain.CharacteristicDisplayInfo;
import it.deltax.produlytics.uibackend.common.machine.business.ports.in.FindCharacteristicInfoUseCase;
import it.deltax.produlytics.uibackend.common.machine.business.ports.out.FindCharacteristicPort;
import it.deltax.produlytics.uibackend.common.machine.business.ports.out.FindMachinePort;

import java.util.Optional;

public class FindCharacteristicInfoService implements FindCharacteristicInfoUseCase {

    private final FindCharacteristicPort findCharacteristicPort;
    private final FindMachinePort findMachinePort;

    public FindCharacteristicInfoService(
        FindCharacteristicPort findCharacteristicPort,
        FindMachinePort findMachinePort
    ) {
        this.findCharacteristicPort = findCharacteristicPort;
        this.findMachinePort = findMachinePort;
    }

    @Override
    public Optional<CharacteristicDisplayInfo> find(int machineId, int id) {
        return findCharacteristicPort.find(machineId, id)
            .flatMap(characteristic -> findMachinePort.find(machineId)
                .map(machine ->
                    new CharacteristicDisplayInfo(machine, characteristic)
                )
            );
    }
}
