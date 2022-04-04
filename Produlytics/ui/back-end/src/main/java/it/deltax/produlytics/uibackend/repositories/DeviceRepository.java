package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CrudRepository<DeviceEntity, Integer> {
	Iterable<TinyDevice> findByArchived(boolean archived);
}