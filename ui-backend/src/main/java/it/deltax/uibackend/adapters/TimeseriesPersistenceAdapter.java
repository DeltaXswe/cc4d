package it.deltax.uibackend.adapters;

import it.deltax.uibackend.business.domain.TimeseriesPointLight;
import it.deltax.uibackend.business.ports.out.TimeseriesPort;
import it.deltax.uibackend.db.rilevazioni.repository.RilevazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TimeseriesPersistenceAdapter implements TimeseriesPort {

    @Autowired
    RilevazioneRepository repo;

    @Override
    public List<TimeseriesPointLight> filterByCharacteristic(long machine, String characteristicName) {
        return repo.findByCaratteristicaAndMacchina(characteristicName, machine).stream()
                .map(rilevazione -> new TimeseriesPointLight(rilevazione.getValore(), rilevazione.getId(), rilevazione.getAnomalo()))
                .collect(Collectors.toList());
    }
}
