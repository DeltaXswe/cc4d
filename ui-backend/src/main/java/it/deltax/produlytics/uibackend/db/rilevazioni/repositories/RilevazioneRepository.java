package it.deltax.produlytics.uibackend.db.rilevazioni.repositories;

import it.deltax.produlytics.persistence.rilevazioni.Rilevazione;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RilevazioneRepository extends CrudRepository<Rilevazione, Long> {

    // autoimplementazione da parte di spring
    List<Rilevazione> findByIdMacchinaAndIdCaratteristica(long macchina, String caratteristica, Sort sort);

    List<Rilevazione> findByIdMacchinaAndIdCaratteristicaAndIdCreazioneUtcGreaterThan(
            @Param("macchina") long macchina,
            @Param("caratteristica") String caratteristica,
            @Param("creazione_utc") long creazioneUtc,
            Sort sort
    );
}
