package it.deltax.produlytics.uibackend.accounts.web;

import it.deltax.produlytics.uibackend.accounts.business.domain.UpdateAccountPassword;
import it.deltax.produlytics.uibackend.accounts.business.ports.in.UpdateAccountPasswordUseCase;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final UpdateAccountPasswordUseCase updateAccountPasswordUseCase;

    public AccountController(UpdateAccountPasswordUseCase updateAccountPasswordUseCase){
        this.updateAccountPasswordUseCase = updateAccountPasswordUseCase;
    }

    @PutMapping("/{username}/password")
    public ResponseEntity<String> putAccountPassword(
        @PathVariable("username") String username,
        @RequestParam("currentPassword") String currentPassword,
        @RequestParam("newPassword") String newPassword) throws BusinessException {
        updateAccountPasswordUseCase.updatePasswordByUsername(
            new UpdateAccountPassword(username, currentPassword, newPassword));
        return new ResponseEntity<>(NO_CONTENT);
    }
}