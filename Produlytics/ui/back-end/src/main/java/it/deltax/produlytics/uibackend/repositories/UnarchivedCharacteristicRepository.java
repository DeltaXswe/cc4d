package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnarchivedCharacteristicRepository
    extends CrudRepository<CharacteristicEntity, CharacteristicEntityId> {

    List<CharacteristicEntity> findByArchivedFalseAndId_DeviceId(int deviceId);
}
