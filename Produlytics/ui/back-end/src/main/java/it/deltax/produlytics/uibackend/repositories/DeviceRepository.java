package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.DeviceEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface DeviceRepository extends CrudRepository<DeviceEntity, Integer> {

	@Transactional
	@Modifying
	@Query(value = "update device d set d.name = :name where d.id = :id", nativeQuery = true)
	void updateDeviceName(@Param("id") int id, @Param("name") String name);

	@Transactional
	@Modifying
	@Query(value = "update device d set d.archived = :archived where d.id = :id", nativeQuery = true)
	void updateDeviceArchivedStatus(@Param("id") int id, @Param("archived") boolean archived);

	@Transactional
	@Modifying
	@Query(value = "update device d set d.deactivated = :deactivated where d.id = :id", nativeQuery = true)
	void updateDeviceDeactivatedStatus(@Param("id") int id, @Param("deactivated") boolean deactivated);

}
