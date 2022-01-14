package it.deltax.produlytics.uibackend.business.ports;

import it.deltax.produlytics.uibackend.business.domain.CharacteristicInfo;
import it.deltax.produlytics.uibackend.business.domain.DetectionLight;
import it.deltax.produlytics.uibackend.business.domain.MachineLight;
import it.deltax.produlytics.uibackend.business.domain.TimeSeries;
import it.deltax.produlytics.uibackend.business.ports.in.TimeSeriesUseCase;
import it.deltax.produlytics.uibackend.business.ports.out.FindCharacteristicInfoPort;
import it.deltax.produlytics.uibackend.business.ports.out.FindMachinePort;
import it.deltax.produlytics.uibackend.business.ports.out.ListDetectionsPort;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class TimeSeriesService implements TimeSeriesUseCase {

    FindCharacteristicInfoPort findCharacteristicInfoPort;

    FindMachinePort findMachinePort;

    ListDetectionsPort listDetectionsService;

    public TimeSeriesService(FindCharacteristicInfoPort findCharacteristicInfoPort, FindMachinePort findMachinePort, ListDetectionsPort listDetectionsService) {
        this.findCharacteristicInfoPort = findCharacteristicInfoPort;
        this.findMachinePort = findMachinePort;
        this.listDetectionsService = listDetectionsService;
    }

    @Override
    public Optional<TimeSeries> getTimeSeries(long machineId, String characteristicName) {
        try {
            MachineLight machine = findMachinePort.find(machineId).orElseThrow();
            CharacteristicInfo info = findCharacteristicInfoPort.find(machineId, characteristicName).orElseThrow();
            List<DetectionLight> detections = listDetectionsService.listDetections(machineId, characteristicName, Optional.empty());

            return Optional.of(new TimeSeries(
                    detections,
                    info.upperLimit(),
                    info.lowerLimit(),
                    info.average(),
                    machine.name()
            ));
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }

    }
}
