package it.deltax.produlytics.uibackend.users.web;

import it.deltax.produlytics.uibackend.devices.business.ports.in.ChangeUserPasswordUseCase;

public class UsersController {
    private final ChangeUserPasswordUseCase useCase;

    public UsersController(ChangeUserPasswordUseCase useCase){ this.useCase = useCase; }

    public putUserPassword(String username, String currentPassword, String hashedPassword){

    }

}