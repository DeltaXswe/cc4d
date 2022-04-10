package it.deltax.produlytics.uibackend.accounts.web;

import com.fasterxml.jackson.databind.JsonNode;
import it.deltax.produlytics.uibackend.accounts.business.domain.AccountPasswordToUpdate;
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
    public ResponseEntity<String> updateAccountPassword(
        @PathVariable("username") String username,
        @RequestBody JsonNode body) throws BusinessException {
        String currentPassword = body.get("currentPassword").toString();
        String newPassword = body.get("newPassword").toString();
        this.updateAccountPasswordUseCase.updatePasswordByUsername(new AccountPasswordToUpdate(
            username,
            currentPassword,
            newPassword));
        return new ResponseEntity<>(NO_CONTENT);
    }
}