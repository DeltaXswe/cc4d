package it.deltax.produlytics.uibackend.accounts.web;

import com.fasterxml.jackson.databind.JsonNode;
import it.deltax.produlytics.uibackend.accounts.business.domain.AccountPasswordToUpdate;
import it.deltax.produlytics.uibackend.accounts.business.ports.in.UpdateAccountPasswordUseCase;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Il controller per le richieste effettuate dagli utenti
 * @author Leila Dardouri
 */
@RestController
@RequestMapping("/accounts")
public class AccountController {
    private final UpdateAccountPasswordUseCase updateAccountPasswordUseCase;


    /**
     * Il costruttore
     * @param updateAccountPasswordUseCase l'interfaccia per il caso d'uso di aggiornamento password
     */
    public AccountController(UpdateAccountPasswordUseCase updateAccountPasswordUseCase){
        this.updateAccountPasswordUseCase = updateAccountPasswordUseCase;
    }


    /**
     * Riceve le chiamate all'endpoint REST per l'aggiornamento della password di un utente
     * @param username l'username dell'utente che vuole aggiornare la password
     * @param body il corpo della richiesta HTTP
     * @return lo stato HTTP
     * @throws BusinessException se l'utente non esiste o la nuova password non è valida
     */
    @PutMapping("/{username}/password")
    public ResponseEntity<String> updateAccountPassword(
        @PathVariable("username") String username,
        @RequestBody JsonNode body) throws BusinessException {
        String currentPassword = body.get("currentPassword").asText();
        String newPassword = body.get("newPassword").asText();
        this.updateAccountPasswordUseCase.updatePasswordByUsername(new AccountPasswordToUpdate(
            username,
            currentPassword,
            newPassword));
        return new ResponseEntity<>(NO_CONTENT);
    }
}