package it.deltax.produlytics.uibackend.admins.web;

import it.deltax.produlytics.uibackend.admins.business.ports.in.ChangeAccountAdminUseCase;
import it.deltax.produlytics.uibackend.admins.business.ports.in.InsertAccountUseCase;
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

	public AdminsController(
		ChangeAccountAdminUseCase changeAccountUseCase,
		InsertAccountUseCase insertAccountUseCase,
		ModifyDeviceUseCase modifyDeviceUseCase
	){
		this.changeAccountUseCase = changeAccountUseCase;
		this.insertAccountUseCase = insertAccountUseCase;
		this.modifyDeviceUseCase = modifyDeviceUseCase;
	}

	@PostMapping("/accounts")
	public ResponseEntity<String> insertAccount(
		@PathVariable("username") String username,
		@RequestParam("password") String password,
		@RequestParam("administrator") boolean administrator){
		if(password.length() < 6)
			return new ResponseEntity<>("invalidNewPassword", BAD_REQUEST); //400
		else{
			if(insertAccountUseCase.insertAccount(username, password, administrator))
				return new ResponseEntity<>(OK); //devo restituire l'username nel body (?)
			else
				return new ResponseEntity<>("duplicateUsername", BAD_REQUEST);
		}

	}

	@PutMapping("/{username}")
	public ResponseEntity<String> modifyAccount(
		@PathVariable("username") String username,
		@RequestParam("newPassword") String newPassword,
		@RequestParam("administrator") boolean administrator) {
		if(!newPassword.isEmpty() && newPassword.length() < 6)
			return new ResponseEntity<>("invalidNewPassword", BAD_REQUEST); //400
		else if(changeAccountUseCase.changeByUsername(username, newPassword, administrator)) {
			return new ResponseEntity<>(NO_CONTENT); //204
		} else {
			return new ResponseEntity<>(BAD_REQUEST); //TODO temporaneo
		}
	}

	@PutMapping("devices/{deviceId}/name")
	public ResponseEntity<String> modifyDevice(
		@PathVariable("deviceId") int deviceId,
		@RequestParam("name") String name){
		if(modifyDeviceUseCase.modifyDevice(deviceId, name))
			return new ResponseEntity<>(NO_CONTENT);
		else
			return new ResponseEntity<>("deviceNotFound", NOT_FOUND);
	}
}