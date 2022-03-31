package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.AccountEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AdminRepository extends CrudRepository<AccountEntity, String> {

	Optional<AccountEntity> findByUsername(String username);

	@Transactional
	@Modifying
	@Query(value = "update device d set d.name = :name where d.id = :id", nativeQuery = true)
	int updateDeviceName(@Param("id") int id, @Param("name") String name);

	@Transactional
	@Modifying
	@Query(value = "update device d set d.archived = :archived where d.id = :id", nativeQuery = true)
	int updateDeviceArchivedStatus(@Param("id") int id, @Param("archived") boolean archived);

}
