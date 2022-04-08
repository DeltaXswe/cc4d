package it.deltax.produlytics.api.repositories;

import it.deltax.produlytics.persistence.DeviceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// Repository per interagire con la tabella `device`.
@Repository
@SuppressWarnings("unused")
public interface DeviceRepository extends CrudRepository<DeviceEntity, Integer> {
	Optional<DeviceEntity> findByApiKey(String apiKey);
}
