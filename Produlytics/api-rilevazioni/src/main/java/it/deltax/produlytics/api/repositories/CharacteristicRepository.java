package it.deltax.produlytics.api.repositories;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@SuppressWarnings("unused")
public interface CharacteristicRepository extends CrudRepository<CharacteristicEntity, CharacteristicEntityId> {}
