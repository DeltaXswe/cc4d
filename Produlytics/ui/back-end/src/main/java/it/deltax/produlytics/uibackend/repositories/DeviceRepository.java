package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Rappresenta il repository delle macchine
 * @author Leila Dardouri
 */
@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Integer> {
	Iterable<TinyDevice> findByArchived(boolean archived);
}
