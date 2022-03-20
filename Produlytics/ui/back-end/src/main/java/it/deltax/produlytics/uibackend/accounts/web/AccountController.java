package it.deltax.produlytics.uibackend.accounts.web;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.in.ChangeAccountPasswordUseCase;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final ChangeAccountPasswordUseCase useCase;

    public AccountController(ChangeAccountPasswordUseCase useCase){this.useCase = useCase; }

    @PutMapping("/{username}/password") //se non funziona RequestParam va usato RequestBody
    public ResponseEntity<String> putAccountPassword(
        @PathVariable("username") String username,
        @RequestParam("currentPassword") String currentPassword,
        @RequestParam("newPassword") String newPassword) {
        if(newPassword.length() < 6)
            return new ResponseEntity<>("invalidNewPassword", BAD_REQUEST); //400
        if(useCase.changeByUsername(username, currentPassword, newPassword)) {
            return new ResponseEntity<>(NO_CONTENT); //204
        }
        else {
            return new ResponseEntity<>("wrongCurrentPassword", FORBIDDEN); //404
        }
    }
}