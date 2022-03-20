package it.deltax.produlytics.uibackend.admins.web;

import it.deltax.produlytics.uibackend.admins.business.ports.in.ChangeAccountAdminUseCase;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/accounts")
public class AdminsController {
	private final ChangeAccountAdminUseCase useCase;

	public AdminsController(ChangeAccountAdminUseCase useCase){this.useCase = useCase; }

	@PostMapping("/{username}")
	public ResponseEntity<String> putAccountPassword(
		@PathVariable("username") String username,
		@RequestParam("newPassword") String newPassword,
		@RequestParam("administrator") boolean administrator) {
		if(!newPassword.isEmpty() && newPassword.length() < 6)
			return new ResponseEntity<>("invalidNewPassword", BAD_REQUEST); //400
		else if(useCase.changeByUsername(username, newPassword, administrator)) {
			return new ResponseEntity<>(NO_CONTENT); //204
		} else {
			return new ResponseEntity<>(BAD_REQUEST); //TODO temporaneo
		}
	}
}