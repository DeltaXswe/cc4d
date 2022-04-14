package it.deltax.produlytics.uibackend.admins.accounts.web;

import com.fasterxml.jackson.databind.JsonNode;
import it.deltax.produlytics.uibackend.accounts.business.domain.AccountDataToUpdate;
import it.deltax.produlytics.uibackend.accounts.business.domain.AccountTiny;
import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountArchiveStatus;
import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountToInsert;
import it.deltax.produlytics.uibackend.admins.accounts.business.domain.AccountUpdatedByAdmin;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.GetAccountsUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.InsertAccountUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.UpdateAccountArchiveStatusUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.UpdateAccountByAdminUseCase;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Il controller per le richieste effettuate dagli amministratori relative agli utenti
 */
@RestController
@RequestMapping("/admin")
public class AdminsAccountsController {
	private final InsertAccountUseCase insertAccountUseCase;
	private final GetAccountsUseCase getAccountsUseCase;
	private final UpdateAccountByAdminUseCase updateAccountByAdminUseCase;
	private final UpdateAccountArchiveStatusUseCase updateAccountArchiveStatusUseCase;


	/**
	 * Il costruttore
	 * @param insertAccountUseCase l'interfaccia per il caso d'uso d'inserimento di un utente
	 * @param getAccountsUseCase l'interfaccia per il caso d'uso di ottenimento degli utenti
	 * @param updateAccountByAdminUseCase l'interfaccia per il caso d'uso di aggiornamento di un utente
	 *                                    per conto di un amministratore
	 * @param updateAccountArchiveStatusUseCase l'interfaccia per il caso d'uso di aggiornamento dello stato di
	 *                                          archiviazione di un utente
	 */
	public AdminsAccountsController(
		InsertAccountUseCase insertAccountUseCase,
		GetAccountsUseCase getAccountsUseCase,
		UpdateAccountByAdminUseCase updateAccountByAdminUseCase,
		UpdateAccountArchiveStatusUseCase updateAccountArchiveStatusUseCase) {
		this.insertAccountUseCase = insertAccountUseCase;
		this.getAccountsUseCase = getAccountsUseCase;
		this.updateAccountByAdminUseCase = updateAccountByAdminUseCase;
		this.updateAccountArchiveStatusUseCase = updateAccountArchiveStatusUseCase;
	}

	/**
	 * Riceve le chiamate all'endpoint REST per l'inserimento di un utente
	 * @param account l'utente da inserire
	 * @return l'id dell'utente inserito e lo stato HTTP
	 * @throws BusinessException se l'utente esiste già o la password non è valida
	 */
	@PostMapping("/accounts")
	public ResponseEntity<Map<String, String>> insertAccount(
		@RequestBody AccountToInsert account) throws BusinessException {
		this.insertAccountUseCase.insertAccount(account);
		Map<String, String> map = new HashMap<>();
		map.put("username", account.username());
		return ResponseEntity.ok(map);
	}

	/**
	 * Riceve le chiamate all'endpoint REST per l'ottenimento degli utenti
	 * @return la lista degli utenti
	 */
	@GetMapping("/accounts")
	public ResponseEntity<List<AccountTiny>> getAccounts() {
		return ResponseEntity.ok(this.getAccountsUseCase.getAccounts());
	}

	/**
	 * Riceve le chiamate all'endpoint REST per l'aggiornamento di un utente
	 * @param username l'username dell'utente da aggiornare
	 * @param body il corpo della richiesta HTTP
	 * @return lo stato HTTP
	 * @throws BusinessException se l'utente non è stato trovato o la nuova password non è valida
	 */
	@PutMapping("/{username}")
	public ResponseEntity<String> updateAccount(
		@PathVariable("username") String username,
		@RequestBody(required=false) AccountDataToUpdate body) throws BusinessException {
		this.updateAccountByAdminUseCase.updateByUsername(
			new AccountUpdatedByAdmin(username, body.newPassword(), body.administrator()));
		return new ResponseEntity<>(NO_CONTENT);
	}

	/**
	 * Riceve le chiamate all'endpoint REST per l'aggiornamento dello stato di archiviazione di un utente
	 * @param username l'username dell'utente da aggiornare
	 * @param body il corpo della richiesta HTTP
	 * @return lo stato HTTP
	 * @throws BusinessException se l'utente non è stato trovato
	 */
	@PutMapping("accounts/{username}/archived")
	public ResponseEntity<String> updateAccountArchiveStatus(
		@PathVariable("username") String username,
		@RequestBody JsonNode body) throws BusinessException {
		boolean archived = body.get("archived").asBoolean();
		this.updateAccountArchiveStatusUseCase.updateAccountArchiveStatus(
			new AccountArchiveStatus(username, archived));
		return new ResponseEntity<>(NO_CONTENT);
	}
}
