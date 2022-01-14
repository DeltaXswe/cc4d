package it.deltax.produlytics.uibackend.adapters;

import it.deltax.produlytics.persistence.rilevazioni.Rilevazione;
import it.deltax.produlytics.uibackend.business.domain.DetectionLight;
import it.deltax.produlytics.uibackend.business.ports.out.FilterDetectionsCreatedAfterPort;
import it.deltax.produlytics.uibackend.business.ports.out.DetectionByCharacteristicPort;
import it.deltax.produlytics.uibackend.db.rilevazioni.repositories.RilevazioneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Component
public class DetectionsPersistenceAdapter implements DetectionByCharacteristicPort, FilterDetectionsCreatedAfterPort {

    @Autowired
    RilevazioneRepository repo;

    @Override
    public List<DetectionLight> filterByCharacteristic(long machine, String characteristicName) {
        return repo.findByCaratteristicaAndMacchina(characteristicName, machine).stream()
                .map(rilevazione -> new DetectionLight(rilevazione.getValore(), rilevazione.getCreazioneUtc(), rilevazione.getAnomalo()))
                .collect(Collectors.toList());
    }

    @Override
    public List<DetectionLight> listAllAfter(long machine, String characteristic, long lastUtc) {
        // TODO
        return  StreamSupport.stream(repo.findAll().spliterator(), false)
                .map(rilevazione -> new DetectionLight(rilevazione.getValore(), rilevazione.getCreazioneUtc(), rilevazione.getAnomalo()))
                .collect(Collectors.toList());
    }
}
