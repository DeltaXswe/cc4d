package it.deltax.produlytics.uibackend.users.web;

import it.deltax.produlytics.uibackend.users.business.ports.in.ChangeUserPasswordUseCase;

public class UsersController {
    private final ChangeUserPasswordUseCase useCase;

    public UsersController(ChangeUserPasswordUseCase useCase){ this.useCase = useCase; }

    public void putUserPassword(String username, String currentPassword, String hashedPassword){

    }

}