package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, String> {
    @Transactional
    @Modifying
    @Query(value = "update Account a set a.hashed_password = :hashed_password where a.username = :username",
           nativeQuery = true)
    void updateAccount(
            @Param("username") String username,
            @Param("hashed_password") String hashed_password
    );

    Optional<AccountEntity> findByUsername(String username);

}