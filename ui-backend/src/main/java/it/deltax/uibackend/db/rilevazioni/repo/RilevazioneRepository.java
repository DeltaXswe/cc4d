package it.deltax.uibackend.db.rilevazioni.repo;

import it.deltax.uibackend.db.rilevazioni.model.Rilevazione;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RilevazioneRepository extends CrudRepository<Rilevazione, Long> {

    // autoimplementazione da parte di spring
    List<Rilevazione> findByCaratteristicaAndMacchina(String caratteristica, long macchina);
}
