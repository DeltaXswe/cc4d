package it.deltax.produlytics.uibackend.business.ports;

import it.deltax.produlytics.uibackend.business.domain.CharacteristicDetections;
import it.deltax.produlytics.uibackend.business.domain.MachineLight;
import it.deltax.produlytics.uibackend.business.domain.DetectionLight;
import it.deltax.produlytics.uibackend.business.ports.in.GetCharacteristicDetectionsUseCase;
import it.deltax.produlytics.uibackend.business.domain.CharacteristicInfo;
import it.deltax.produlytics.uibackend.business.ports.out.FindCharacteristicInfoPort;
import it.deltax.produlytics.uibackend.business.ports.out.FindMachinePort;
import it.deltax.produlytics.uibackend.business.ports.out.DetectionByCharacteristicPort;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class GetCharacteristicDetectionsService implements GetCharacteristicDetectionsUseCase {

    private DetectionByCharacteristicPort detectionByCharacteristicPort;

    private FindMachinePort findMachinePort;

    private FindCharacteristicInfoPort findCharacteristicInfoPort;

    public GetCharacteristicDetectionsService(DetectionByCharacteristicPort detectionByCharacteristicPort, FindMachinePort findMachinePort, FindCharacteristicInfoPort findCharacteristicInfoPort) {
        this.detectionByCharacteristicPort = detectionByCharacteristicPort;
        this.findMachinePort = findMachinePort;
        this.findCharacteristicInfoPort = findCharacteristicInfoPort;
    }

    @Override
    public Optional<CharacteristicDetections> getCharacteristicDetections(long machine, String characteristicName) {
        try {
            MachineLight macchina = findMachinePort.find(machine)
                    .orElseThrow();
            CharacteristicInfo info = findCharacteristicInfoPort.find(machine, characteristicName)
                    .orElseThrow();
            List<DetectionLight> rilevazioni = detectionByCharacteristicPort.filterByCharacteristic(machine, characteristicName);

            return Optional.of(new CharacteristicDetections(
                    rilevazioni,
                    info.limiteMax(),
                    info.limiteMin(),
                    info.media(),
                    macchina.name()
            ));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }


    }
}
