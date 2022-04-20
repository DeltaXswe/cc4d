package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Rappresenta il repository delle macchine
 */
@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Integer> {
	Iterable<TinyDevice> findByArchived(boolean archived);
	List<TinyDevice> findByName(String name);
}
