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
    @Query(
    value = """
    select * from (
        select * from rilevazione r where 
            r.macchina = ?1 and 
            r.caratteristica = ?2 
            order by creazione_utc desc 
            limit 100
        ) newest 
        order by newest.creazione_utc asc
    """, nativeQuery = true
    )
    List<Rilevazione> findByIdMacchinaAndIdCaratteristica(
            @Param("macchina") long macchina,
            @Param("caratteristica") String caratteristica
    );

    List<Rilevazione> findByIdMacchinaAndIdCaratteristicaAndIdCreazioneUtcGreaterThan(
            @Param("macchina") long macchina,
            @Param("caratteristica") String caratteristica,
            @Param("creazione_utc") long creazioneUtc,
            Sort sort
    );
}
