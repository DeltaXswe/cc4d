package it.deltax.produlytics.uibackend.users.adapters;

import it.deltax.produlytics.uibackend.devices.business.ports.in.UpdateUserPort;
import it.deltax.produlytics.uibackend.devices.business.ports.in.ChangeUserPasswordUseCase;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter implements UpdateUserPort, ChangeUserPasswordUseCase {
    private final UserRepository repo;

    public UserAdapter(UserRepository repo) { this.repo = repo; }

    @Override
    public boolean updateUser(String username, hashedPassword){

    }

    @Override
    public User findByUsername(String username) {
        return repo.findById(username)
                .map(utente ->
                        new User(
                                utente.getUsername(),
                                utente.getHashedPassword(),
                                utente.getAdministrator(),
                                utente.getArchived())
                        )
                );
    }
}