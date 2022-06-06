package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Rappresenta il repository delle macchine. */
@Repository
public interface DeviceRepository extends JpaRepository<DeviceEntity, Integer> {
  Iterable<TinyDevice> findByArchivedOrderById(boolean archived);

  List<TinyDevice> findByName(String name);

  List<DeviceEntity> findAllByOrderById();
}
