package it.deltax.produlytics.uibackend.repositories;

import it.deltax.produlytics.persistence.AccountEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<AccountEntity, String> {
    @Modifying
    @Query("update User u set u.hashed_password = hashedPassword where u.username = username")
    boolean updateUser((
            @Param("username") String username,
            @Param("hashedPassword") String hashedPassword
    );


}