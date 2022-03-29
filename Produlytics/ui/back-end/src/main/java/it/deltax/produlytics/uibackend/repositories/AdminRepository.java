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
	@Transactional
	@Modifying
	@Query(value = "update account a set a.hashed_password = :hashed_password, a.administratore = :administrator where a.username = :username", nativeQuery = true)
	int updateAccount(
		@Param("username") String username,
		@Param("hashed_password") String hashed_password,
		@Param("administrator") boolean administrator
	);

	@Transactional
	@Modifying
	@Query(value = "update account a set a.administratore = :administrator where a.username = :username", nativeQuery = true)
	int updateAccountPrivileges(
		@Param("username") String username,
		@Param("administrator") boolean administrator
	);

	Optional<AccountEntity> findByUsername(String username);

	@Transactional
	@Modifying //TODO administratorE perché vede la vecchia versione dell'sql, dove era stato scritto administratore. CORREGGERE
	@Query(value = "insert into account (username, hashed_password, administratore, archived) values (:username, :hashed_password, :administrator, :archived)", nativeQuery = true)
	void insertAccount(
		@Param("username") String username,
		@Param("hashed_password") String hashed_password,
		@Param("administrator") boolean administrator,
		@Param("archived") boolean archived
	);

	@Transactional
	@Modifying
	@Query(value = "update device d set d.name = :name where d.id = :id", nativeQuery = true)
	int updateDeviceName(@Param("id") int id, @Param("name") String name);

	@Transactional
	@Modifying
	@Query(value = "update device d set d.archived = :archived where d.id = :id", nativeQuery = true)
	int updateDeviceArchivedStatus(@Param("id") int id, @Param("archived") boolean archived);

}
