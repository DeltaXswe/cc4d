package it.deltax.produlytics.api.repositories;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CharacteristicRepository extends CrudRepository<CharacteristicEntity, CharacteristicEntityId> {
	// TODO: Change query
	@Query("SELECT "
		+ "machine.api_key AS apiKey, "
		+ "device.archived AS deviceArchived, "
		+ "device.deactivated AS deviceDeactivated, "
		+ "characteristic.archived AS characteristicArchived "
		+ "FROM characteristic "
		+ "JOIN device ON characteristic.device_id = device.id "
		+ "WHERE characteristic.id = :characteristicId")
	Optional<ValidationInfoProjection> findValidationInfo(
		@Param("apiKey") String apiKey, @Param("characteristicId") int charateristicId
	);
}
