package it.deltax.produlytics.api.adapters;

import it.deltax.produlytics.api.business.domain.Detection;
import it.deltax.produlytics.api.business.domain.DetectionLight;
import it.deltax.produlytics.api.business.ports.out.InsertDetectionPort;
import it.deltax.produlytics.api.db.repositories.RilevazioneRepository;
import it.deltax.produlytics.persistence.rilevazioni.Rilevazione;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class DetectionPersistenceAdapter implements InsertDetectionPort {

    @Autowired
    private RilevazioneRepository repo;

    @Override
    public Detection insertRilevazione(DetectionLight rilevazione) {
        Rilevazione nuova = new Rilevazione(Instant.now().getEpochSecond(), rilevazione.caratteristica(), rilevazione.macchina(), rilevazione.value(), false);
        Rilevazione salvata = repo.save(nuova);
        
        return new Detection(salvata.getMacchina(), 
                        salvata.getCaratteristica(), salvata.getValore(), 
                        salvata.getAnomalo(), salvata.getCreazioneUtc());      

    }
}
