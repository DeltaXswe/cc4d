package it.deltax.produlytics.uibackend.users.web;

import it.deltax.produlytics.uibackend.users.business.ports.in.ChangeAccountPasswordUseCase;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final ChangeAccountPasswordUseCase useCase;

    public AccountController(ChangeAccountPasswordUseCase useCase){this.useCase = useCase; }

    @PostMapping("/{username}/password")
    public void putAccountPassword(@PathVariable("username") String username, String currentPassword, String hashedPassword){
        useCase.changeByUsername(username, currentPassword, hashedPassword);
    }

}