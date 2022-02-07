package it.deltax.produlytics.uibackend.db.configurazione.repositories;

import it.deltax.produlytics.persistence.configurazione.Caratteristica;
import it.deltax.produlytics.persistence.configurazione.CaratteristicaId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CaratteristicaRepository extends CrudRepository<Caratteristica, CaratteristicaId> {
    List<Caratteristica> findByIdMacchina(long macchina);
}
