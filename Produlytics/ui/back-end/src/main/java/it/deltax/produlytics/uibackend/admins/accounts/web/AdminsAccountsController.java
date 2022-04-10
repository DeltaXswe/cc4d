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
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminsAccountsController {
	private final InsertAccountUseCase insertAccountUseCase;
	private final GetAccountsUseCase getAccountsUseCase;
	private final UpdateAccountByAdminUseCase updateAccountByAdminUseCase;
	private final UpdateAccountArchiveStatusUseCase updateAccountArchiveStatusUseCase;

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

	@PostMapping("/accounts")
	public ResponseEntity<Map<String, String>> insertAccount(
		@RequestBody AccountToInsert account) throws BusinessException {
		this.insertAccountUseCase.insertAccount(account);
		Map<String, String> map = new HashMap<>();
		map.put("username", account.username());
		return ResponseEntity.ok(map);
	}

	@GetMapping("/accounts")
	public ResponseEntity<List<AccountTiny>> getAccounts() throws BusinessException {
		return ResponseEntity.ok(this.getAccountsUseCase.getAccounts());
	}

	@PutMapping("/{username}")
	public ResponseEntity<String> updateAccount(
		@PathVariable("username") String username,
		@RequestBody(required=false) AccountDataToUpdate body) throws BusinessException {
		this.updateAccountByAdminUseCase.updateByUsername(
			new AccountUpdatedByAdmin(username, body.newPassword(), body.administrator()));
		return new ResponseEntity<>(NO_CONTENT);
	}

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
