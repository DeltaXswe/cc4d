package it.deltax.produlytics.uibackend.admins.repositories;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CharacteristicRepository extends JpaRepository<CharacteristicEntity, CharacteristicEntityId> {
	List<CharacteristicEntity> findByName(String name);
}
