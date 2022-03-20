package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.AccountEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountAdminRepository extends CrudRepository<AccountEntity, String> {
	@Modifying
	@Query("update Account a set a.hashed_password = hashedPassword and a.administrator = administrator where a.username = username")
	boolean updateAccount(
		@Param("username") String username,
		@Param("hashedPassword") String hashedPassword,
		@Param("administrator") boolean administrator
	);

	@Modifying
	@Query("update Account a set a.administrator = administrator where a.username = username")
	boolean updateAccountPrivileges(
		@Param("username") String username,
		@Param("administrator") boolean administrator
	);

	Optional<AccountEntity> findByUsername(String username);

}
