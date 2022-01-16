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
    @Query("select r from rilevazione r where r.macchina = ?1 and r.caratteristica = ?2 order by creazione_utc")
    List<Rilevazione> findByMacchinaAndCaratteristica(long macchina, String caratteristica);

    @Query("select r from rilevazione r where r.macchina = :macchina and r.caratteristica = :caratteristica and r.creazione_utc > :creazione_utc order by creazione_utc")
    List<Rilevazione> findByMacchinaAndCaratteristicaAndCreazioneUtcGreaterThan(
            @Param("macchina") long macchina,
            @Param("caratteristica") String caratteristica,
            @Param("creazione_utc") long creazioneUtc);
}
