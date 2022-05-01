package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/** Rappresenta il repository delle caratteristiche. */
@Repository
public interface CharacteristicRepository
    extends JpaRepository<CharacteristicEntity, CharacteristicEntityId> {
  List<CharacteristicEntity> findByArchivedFalseAndDeviceId(int deviceId);

  Optional<CharacteristicEntity> findByArchivedFalseAndDeviceIdAndId(
      int deviceId, int characteristicId);

  List<CharacteristicEntity> findByDeviceId(int deviceId);

  @Query(
      value = "SELECT * FROM characteristic WHERE device_id = :deviceId AND name = :name",
      nativeQuery = true)
  Optional<CharacteristicEntity> findByDeviceIdAndName(
      @Param("deviceId") int deviceId, @Param("name") String name);
}
