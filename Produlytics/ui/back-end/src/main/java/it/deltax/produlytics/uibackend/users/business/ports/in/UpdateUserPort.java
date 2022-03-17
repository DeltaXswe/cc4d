package it.deltax.produlytics.uibackend.users.business.ports.in;

import it.deltax.produlytics.uibackend.users.business.domain.User;

public interface UpdateUserPort {
    boolean updateUser(User username);
}