package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, String> {
    Optional<Account> findByUsername(String username);

    @Transactional
    @Modifying
    @Query(value = "update device d set d.name = :name where d.id = :id", nativeQuery = true)
    int updateDeviceName(@Param("id") int id, @Param("name") String name);

    @Transactional
    @Modifying
    @Query(value = "update device d set d.archived = :archived where d.id = :id", nativeQuery = true)
    int updateDeviceArchivedStatus(@Param("id") int id, @Param("archived") boolean archived);

}