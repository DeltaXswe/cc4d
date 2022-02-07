package it.deltax.produlytics.api.db.configurazione.repositories;

import it.deltax.produlytics.persistence.configurazione.Caratteristica;
import it.deltax.produlytics.persistence.configurazione.CaratteristicaId;
import org.springframework.data.repository.CrudRepository;

public interface CaratteristicaRepository extends CrudRepository<Caratteristica, CaratteristicaId> {
}
