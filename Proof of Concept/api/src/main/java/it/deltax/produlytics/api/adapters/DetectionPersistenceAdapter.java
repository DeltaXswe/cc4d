package it.deltax.produlytics.api.adapters;

import it.deltax.produlytics.api.business.domain.Detection;
import it.deltax.produlytics.api.business.domain.DetectionLight;
import it.deltax.produlytics.api.business.ports.out.InsertDetectionPort;
import it.deltax.produlytics.api.db.rilevazioni.repositories.RilevazioneRepository;
import it.deltax.produlytics.persistence.rilevazioni.Rilevazione;
import it.deltax.produlytics.persistence.rilevazioni.RilevazioneId;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DetectionPersistenceAdapter implements InsertDetectionPort {

    @Autowired
    private RilevazioneRepository repo;

    @Override
    public Detection insertDetection(DetectionLight rilevazione) {
        RilevazioneId id = new RilevazioneId(Instant.now().getEpochSecond(), rilevazione.characteristic(), rilevazione.machine());
        Rilevazione nuova = new Rilevazione(id, rilevazione.value(), rilevazione.anomalous());
        Rilevazione salvata = repo.save(nuova);
        
        return new Detection(salvata.getID().getMacchina(),
                        salvata.getID().getCaratteristica(), salvata.getValore(),
                        salvata.getAnomalo(), salvata.getID().getCreazioneUtc());

    }
}
