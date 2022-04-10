package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Rappresenta il repository delle caratteristiche
 * @author Alberto Lazari
 */
public interface CharacteristicRepository extends JpaRepository<CharacteristicEntity, CharacteristicEntityId> {
    List<CharacteristicEntity> findByArchivedFalseAndDeviceId(int deviceId);
    List<CharacteristicEntity> findByName(String name);
}
