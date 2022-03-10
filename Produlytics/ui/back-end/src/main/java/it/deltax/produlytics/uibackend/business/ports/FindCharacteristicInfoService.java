package it.deltax.produlytics.uibackend.business.ports;

import it.deltax.produlytics.uibackend.business.domain.CharacteristicDisplayInfo;
import it.deltax.produlytics.uibackend.business.ports.in.FindCharacteristicInfoUseCase;
import it.deltax.produlytics.uibackend.business.ports.out.FindCharacteristicPort;
import it.deltax.produlytics.uibackend.business.ports.out.FindMachinePort;

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
    public Optional<CharacteristicDisplayInfo> find(long machineId, String code) {
        return findCharacteristicPort.find(machineId, code)
            .flatMap(characteristic -> findMachinePort.find(machineId)
                .map(machine ->
                    new CharacteristicDisplayInfo(machine, characteristic)
                )
            );
    }
}
