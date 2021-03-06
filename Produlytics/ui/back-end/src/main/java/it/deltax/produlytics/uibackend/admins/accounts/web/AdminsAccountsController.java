package it.deltax.produlytics.uibackend.admins.accounts.web;

import com.fasterxml.jackson.databind.JsonNode;
import it.deltax.produlytics.uibackend.accounts.business.domain.AccountDataToUpdate;
import it.deltax.produlytics.uibackend.accounts.business.domain.TinyAccount;
import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountArchiveStatus;
import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountToInsert;
import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountUpdatedByAdmin;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.GetTinyAccountsUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.InsertAccountUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.UpdateAccountArchiveStatusUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.UpdateAccountByAdminUseCase;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Il controller per le richieste effettuate dagli amministratori relative agli utenti. */
@RestController
@RequestMapping("/admin")
public class AdminsAccountsController {
  private final InsertAccountUseCase insertAccountUseCase;
  private final GetTinyAccountsUseCase getTinyAccountsUseCase;
  private final UpdateAccountByAdminUseCase updateAccountByAdminUseCase;
  private final UpdateAccountArchiveStatusUseCase updateAccountArchiveStatusUseCase;

  /**
   * Il costruttore.
   *
   * @param insertAccountUseCase l'interfaccia per il caso d'uso d'inserimento di un utente
   * @param getAccountsUseCase l'interfaccia per il caso d'uso di ottenimento degli utenti
   * @param updateAccountByAdminUseCase l'interfaccia per il caso d'uso di aggiornamento di un
   *     utente per conto di un amministratore
   * @param updateAccountArchiveStatusUseCase l'interfaccia per il caso d'uso di aggiornamento dello
   *     stato di archiviazione di un utente
   */
  public AdminsAccountsController(
      InsertAccountUseCase insertAccountUseCase,
      GetTinyAccountsUseCase getAccountsUseCase,
      UpdateAccountByAdminUseCase updateAccountByAdminUseCase,
      UpdateAccountArchiveStatusUseCase updateAccountArchiveStatusUseCase) {
    this.insertAccountUseCase = insertAccountUseCase;
    this.getTinyAccountsUseCase = getAccountsUseCase;
    this.updateAccountByAdminUseCase = updateAccountByAdminUseCase;
    this.updateAccountArchiveStatusUseCase = updateAccountArchiveStatusUseCase;
  }

  /**
   * Riceve le chiamate all'endpoint REST per l'inserimento di un utente.
   *
   * @param account l'utente da inserire
   * @return l'id dell'utente inserito e lo stato HTTP
   * @throws BusinessException se l'utente esiste gi?? o la password non ?? valida
   */
  @PostMapping("/accounts")
  public ResponseEntity<Map<String, String>> insertAccount(@RequestBody AccountToInsert account)
      throws BusinessException {
    this.insertAccountUseCase.insertAccount(account);
    Map<String, String> map = new HashMap<>();
    map.put("username", account.username());
    return ResponseEntity.ok(map);
  }

  /**
   * Riceve le chiamate all'endpoint REST per l'ottenimento degli utenti.
   *
   * @return la lista degli utenti
   */
  @GetMapping("/accounts")
  public ResponseEntity<List<TinyAccount>> getAccounts() {
    return ResponseEntity.ok(this.getTinyAccountsUseCase.getTinyAccounts());
  }

  /**
   * Riceve le chiamate all'endpoint REST per l'aggiornamento di un utente.
   *
   * @param username l'username dell'utente da aggiornare
   * @param body il corpo della richiesta HTTP
   * @return lo stato HTTP
   * @throws BusinessException se l'utente non ?? stato trovato o la nuova password non ?? valida
   */
  @PutMapping("/accounts/{username}")
  public ResponseEntity<String> updateAccount(
      @PathVariable("username") String username, @RequestBody AccountDataToUpdate body)
      throws BusinessException {
    this.updateAccountByAdminUseCase.updateByUsername(
        new AccountUpdatedByAdmin(username, body.password(), body.administrator()));
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  /**
   * Riceve le chiamate all'endpoint REST per l'aggiornamento dello stato di archiviazione di un
   * utente.
   *
   * @param username l'username dell'utente da aggiornare
   * @param body il corpo della richiesta HTTP
   * @return lo stato HTTP
   * @throws BusinessException se l'utente non ?? stato trovato
   */
  @PutMapping("/accounts/{username}/archived")
  public ResponseEntity<String> updateAccountArchiveStatus(
      @PathVariable("username") String username, @RequestBody JsonNode body)
      throws BusinessException {
    boolean archived = body.asBoolean();
    this.updateAccountArchiveStatusUseCase.updateAccountArchiveStatus(
        new AccountArchiveStatus(username, archived));
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }
}
