package it.deltax.produlytics.api.repositories;

import it.deltax.produlytics.persistence.DeviceEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface DeviceRepository extends CrudRepository<DeviceEntity, Long> {
	Optional<DeviceEntity> findByApiKey(String apiKey);
}
