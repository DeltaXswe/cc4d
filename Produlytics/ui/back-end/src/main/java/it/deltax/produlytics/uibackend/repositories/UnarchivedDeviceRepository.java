package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.DeviceEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UnarchivedDeviceRepository extends CrudRepository<DeviceEntity, Integer> {
}
