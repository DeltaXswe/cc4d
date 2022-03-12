package it.deltax.produlytics.api.repositories;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import org.springframework.data.repository.CrudRepository;

public interface CharacteristicRepository extends CrudRepository<CharacteristicEntity, CharacteristicEntityId> {}
