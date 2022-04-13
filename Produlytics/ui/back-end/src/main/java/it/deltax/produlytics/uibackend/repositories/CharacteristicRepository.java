package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Rappresenta il repository delle caratteristiche
 */
public interface CharacteristicRepository extends JpaRepository<CharacteristicEntity, CharacteristicEntityId> {
    List<CharacteristicEntity> findByArchivedFalseAndDeviceId(int deviceId);
    Optional<CharacteristicEntity> findByArchivedFalseAndDeviceIdAndId(int deviceId, int characteristicId);

    List<CharacteristicEntity> findByDeviceId(int deviceId);
    List<CharacteristicEntity> findByDeviceIdAndName(int deviceId, String name);
}
