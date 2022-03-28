package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnarchivedCharacteristicRepository
    extends CrudRepository<CharacteristicEntity, CharacteristicEntityId> {

    @Query("""
        select c
        from CharacteristicEntity c
        where c.deviceId = :deviceId
        """)
    List<CharacteristicEntity> findByArchivedFalseAndDeviceId(@Param("deviceId") int deviceId);
}
