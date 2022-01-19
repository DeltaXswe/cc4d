package it.deltax.produlytics.api.db.repositories;

import it.deltax.produlytics.persistence.rilevazioni.Rilevazione;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RilevazioneRepository extends CrudRepository<Rilevazione, Long> {
}
