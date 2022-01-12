package it.deltax.uibackend.db.configurazione.repos;

import it.deltax.uibackend.db.configurazione.model.Caratteristica;
import it.deltax.uibackend.db.configurazione.model.CaratteristicaId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CaratteristicaRepository extends CrudRepository<Caratteristica, CaratteristicaId> {
}
