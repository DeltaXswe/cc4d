package it.deltax.produlytics.api.repositories;

import it.deltax.produlytics.persistence.DeviceEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@SuppressWarnings("unused")
public interface DeviceRepository extends CrudRepository<DeviceEntity, Long> {
	Optional<DeviceEntity> findByApiKey(String apiKey);
}
