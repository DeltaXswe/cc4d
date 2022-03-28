package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.AccountEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends CrudRepository<AccountEntity, String> {
	@Modifying
	@Query("update account a set a.hashed_password = :hashedPassword and a.administrator = :administrator where a.username = :username")
	int updateAccount(
		@Param("username") String username,
		@Param("hashedPassword") String hashedPassword,
		@Param("administrator") boolean administrator
	);

	@Modifying
	@Query("update account a set a.administrator = :administrator where a.username = :username")
	int updateAccountPrivileges(
		@Param("username") String username,
		@Param("administrator") boolean administrator
	);

	Optional<AccountEntity> findByUsername(String username);

	@Modifying
	@Query("insert into account a values (:username, :hashedPassword, :administrator, :archivided)")
	void insertAccount(
		@Param("name") String name,
		@Param("hashedPassword") String hashedPassword,
		@Param("administrator") boolean administrator,
		@Param("archived") boolean archived
	);

	@Modifying
	@Query("update device d set d.name = :name where d.id = :id")
	int updateDeviceName(@Param("id") int id, @Param("name") String name);

	@Modifying
	@Query("update device d set d.archived = :archived where d.id = :id")
	int updateDeviceArchivedStatus(@Param("id") int id, @Param("archived") boolean archived);

}
