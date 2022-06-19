package it.deltax.produlytics.uibackend.accounts.web;

import com.fasterxml.jackson.databind.JsonNode;
import it.deltax.produlytics.uibackend.accounts.business.domain.AccountInfo;
import it.deltax.produlytics.uibackend.accounts.business.domain.AccountPasswordToUpdate;
import it.deltax.produlytics.uibackend.accounts.business.ports.in.UpdateAccountPasswordUseCase;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.security.ProdulyticsGrantedAuthority;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Il controller per le richieste effettuate dagli utenti. */
@RestController
@RequestMapping("/accounts")
public class AccountController {
  @Autowired private final UpdateAccountPasswordUseCase updateAccountPasswordUseCase;

  /**
   * Il costruttore.
   *
   * @param updateAccountPasswordUseCase l'interfaccia per il caso d'uso di aggiornamento password
   */
  public AccountController(UpdateAccountPasswordUseCase updateAccountPasswordUseCase) {
    this.updateAccountPasswordUseCase = updateAccountPasswordUseCase;
  }

  /**
   * Riceve le chiamate all'endpoint REST per l'aggiornamento della password di un utente.
   *
   * @param username l'username dell'utente che vuole aggiornare la password
   * @param body il corpo della richiesta HTTP
   * @return lo stato HTTP
   * @throws BusinessException se l'utente non esiste o la nuova password non è valida
   */
  @PutMapping("/{username}/password")
  public ResponseEntity<String> updateAccountPassword(
      @PathVariable("username") String username, @RequestBody JsonNode body)
      throws BusinessException {
    String currentPassword = body.get("currentPassword").asText();
    String newPassword = body.get("newPassword").asText();
    this.updateAccountPasswordUseCase.updatePasswordByUsername(
        new AccountPasswordToUpdate(username, currentPassword, newPassword));
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Riceve le chiamate all'endpoint REST per ottenere i dati dell'utente corrente.
   *
   * @param authentication i dati di autenticazione di Spring Security
   * @return le informazioni dell'utente corrente
   */
  @GetMapping("/info")
  public AccountInfo getAccountInfo(Authentication authentication) {
    var authorities = authentication.getAuthorities();
    var adminAuthority = ProdulyticsGrantedAuthority.ADMIN.getAuthority();
    var admin = authorities.stream().anyMatch(auth -> auth.getAuthority().equals(adminAuthority));
    var username = authentication.getName();
    return new AccountInfo(username, admin);
  }
}
