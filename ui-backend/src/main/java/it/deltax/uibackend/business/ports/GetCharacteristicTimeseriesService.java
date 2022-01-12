package it.deltax.uibackend.business.ports;

import it.deltax.uibackend.business.domain.CharacteristicInfo;
import it.deltax.uibackend.business.domain.CharacteristicTimeseries;
import it.deltax.uibackend.business.domain.MachineLight;
import it.deltax.uibackend.business.domain.TimeseriesPointLight;
import it.deltax.uibackend.business.ports.in.GetCharacteristicTimeseriesUseCase;
import it.deltax.uibackend.business.ports.out.FindCharacteristicInfoPort;
import it.deltax.uibackend.business.ports.out.FindMachinePort;
import it.deltax.uibackend.business.ports.out.TimeseriesPort;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public class GetCharacteristicTimeseriesService implements GetCharacteristicTimeseriesUseCase {

    private TimeseriesPort timeseriesPort;

    private FindMachinePort findMachinePort;

    private FindCharacteristicInfoPort findCharacteristicInfoPort;

    public GetCharacteristicTimeseriesService(TimeseriesPort timeseriesPort, FindMachinePort findMachinePort, FindCharacteristicInfoPort findCharacteristicInfoPort) {
        this.timeseriesPort = timeseriesPort;
        this.findMachinePort = findMachinePort;
        this.findCharacteristicInfoPort = findCharacteristicInfoPort;
    }

    @Override
    public Optional<CharacteristicTimeseries> getCharacteristicTimeSeries(long machine, String characteristicName) {
        try {
            MachineLight macchina = findMachinePort.find(machine)
                    .orElseThrow();
            CharacteristicInfo info = findCharacteristicInfoPort.find(machine, characteristicName)
                    .orElseThrow();
            List<TimeseriesPointLight> rilevazioni = timeseriesPort.filterByCharacteristic(machine, characteristicName);

            return Optional.of(new CharacteristicTimeseries(
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
