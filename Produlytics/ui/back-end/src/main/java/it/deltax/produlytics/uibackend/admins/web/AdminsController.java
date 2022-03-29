package it.deltax.produlytics.uibackend.admins.web;

import it.deltax.produlytics.uibackend.admins.business.ports.in.ChangeAccountAdminUseCase;
import it.deltax.produlytics.uibackend.admins.business.ports.in.InsertAccountUseCase;
import it.deltax.produlytics.uibackend.admins.business.ports.in.ModDevArchStatusUseCase;
import it.deltax.produlytics.uibackend.admins.business.ports.in.ModifyDeviceUseCase;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
public class AdminsController {
	private final ChangeAccountAdminUseCase changeAccountUseCase;
	private final InsertAccountUseCase insertAccountUseCase;
	private final ModifyDeviceUseCase modifyDeviceUseCase;
	private final ModDevArchStatusUseCase modDevArchStatusUseCase;

	public AdminsController(
		ChangeAccountAdminUseCase changeAccountUseCase,
		InsertAccountUseCase insertAccountUseCase,
		ModifyDeviceUseCase modifyDeviceUseCase,
		ModDevArchStatusUseCase modDevArchStatusUseCase
	){
		this.changeAccountUseCase = changeAccountUseCase;
		this.insertAccountUseCase = insertAccountUseCase;
		this.modifyDeviceUseCase = modifyDeviceUseCase;
		this.modDevArchStatusUseCase = modDevArchStatusUseCase;
	}

	@PostMapping("/accounts")
	public ResponseEntity<String> insertAccount(
		@RequestParam("username") String username,
		@RequestParam("password") String password,
		@RequestParam("administrator") boolean administrator){
		if(password.length() < 6)
			return new ResponseEntity<>("\"errorCode\": \"invalidNewPassword\"", BAD_REQUEST); //400
		else{
			if(insertAccountUseCase.insertAccount(username, password, administrator))
				return new ResponseEntity<>(OK); //devo restituire l'username nel body (?)
			else
				return new ResponseEntity<>("\"errorCode\": \"duplicateUsername\"", BAD_REQUEST);
		}

	}

	@PutMapping("/{username}")
	public ResponseEntity<String> modifyAccount(
		@PathVariable("username") String username,
		@RequestParam("newPassword") String newPassword,
		@RequestParam("administrator") boolean administrator) {
		if(!newPassword.isEmpty() && newPassword.length() < 6)
			return new ResponseEntity<>("\"errorCode\": \"invalidNewPassword\"", BAD_REQUEST); //400
		else if(changeAccountUseCase.changeByUsername(username, newPassword, administrator)) {
			return new ResponseEntity<>(NO_CONTENT);
		} else {
			return new ResponseEntity<>("\"errorCode\": \"accountNotFound\"", NOT_FOUND); //TODO aggiungere alla specifica archittettuarale il caso in cui non si trovi l'account
		}
	}

	/*
	@GetMapping("/devices/{deviceId}")
	public ResponseEntity<String> modifyDevice(
		@PathVariable("deviceId")){

	}
*/

	@PutMapping("devices/{deviceId}/archived")
	public ResponseEntity<String> modifyDeviceArchStatus(
		@PathVariable("deviceId") int deviceId,
		@RequestParam("archived") boolean archived) { //come si chiama il parametro?
		if(modDevArchStatusUseCase.modDevArchStatus(deviceId, archived))
			return new ResponseEntity<>(NO_CONTENT);
		else
			return new ResponseEntity<>("\"errorCode\": \"deviceNotFound\"", NOT_FOUND);
	}


	@PutMapping("/devices/{deviceId}/name")
	public ResponseEntity<String> modifyDevice(
		@PathVariable("deviceId") int deviceId,
		@RequestParam("name") String name){
		if(modifyDeviceUseCase.modifyDevice(deviceId, name))
			return new ResponseEntity<>(NO_CONTENT);
		else
			return new ResponseEntity<>("\"errorCode\": \"deviceNotFound\"", NOT_FOUND);
	}
}