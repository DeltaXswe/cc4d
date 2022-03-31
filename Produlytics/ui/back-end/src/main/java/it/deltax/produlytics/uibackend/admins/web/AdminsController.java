package it.deltax.produlytics.uibackend.admins.web;

import it.deltax.produlytics.uibackend.admins.business.domain.UpdateAdminAccout;
import it.deltax.produlytics.uibackend.admins.business.domain.DeviceArchiveStatus;
import it.deltax.produlytics.uibackend.admins.business.domain.InsertAccount;
import it.deltax.produlytics.uibackend.admins.business.ports.in.UpdateAccountByAdminUseCase;
import it.deltax.produlytics.uibackend.admins.business.ports.in.InsertAccountUseCase;
import it.deltax.produlytics.uibackend.admins.business.ports.in.UpdateDeviceArchiveStatusUseCase;
import it.deltax.produlytics.uibackend.admins.business.ports.in.UpdateDeviceNameUseCase;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminsController {
	private final UpdateAccountByAdminUseCase updateAccountByAdminUseCase;
	private final InsertAccountUseCase insertAccountUseCase;
	private final UpdateDeviceNameUseCase updateDeviceNameUseCase;
	private final UpdateDeviceArchiveStatusUseCase updateDeviceArchiveStatusUseCase;

	public AdminsController(
		UpdateAccountByAdminUseCase updateAccountByAdminUseCase,
		InsertAccountUseCase insertAccountUseCase,
		UpdateDeviceNameUseCase updateDeviceNameUseCase,
		UpdateDeviceArchiveStatusUseCase updateDeviceArchiveStatusUseCase
	){
		this.updateAccountByAdminUseCase = updateAccountByAdminUseCase;
		this.insertAccountUseCase = insertAccountUseCase;
		this.updateDeviceNameUseCase = updateDeviceNameUseCase;
		this.updateDeviceArchiveStatusUseCase = updateDeviceArchiveStatusUseCase;
	}

	@PostMapping("/accounts")
	public ResponseEntity<String> insertAccount(
		@RequestParam("username") String username,
		@RequestParam("password") String password,
		@RequestParam("administrator") boolean administrator) throws BusinessException {
		insertAccountUseCase.insertAccount(new InsertAccount(username, password, administrator));
		return new ResponseEntity<>(OK); //TODO restituire l'username nel body
	}


	@PutMapping("/{username}")
	public ResponseEntity<String> modifyAccount(
		@PathVariable("username") String username,
		@RequestParam("newPassword") Optional<String> newPassword,
		@RequestParam("administrator") boolean administrator) throws BusinessException {
		updateAccountByAdminUseCase.updateByUsername(new UpdateAdminAccout(username, newPassword, administrator));
		return new ResponseEntity<>(NO_CONTENT);
	}


	@PutMapping("devices/{deviceId}/archived")
	public ResponseEntity<String> updateDeviceArchiveStatus(
		@PathVariable("deviceId") int deviceId,
		@RequestParam("archived") boolean archived) throws BusinessException {
		updateDeviceArchiveStatusUseCase.modDevArchStatus(new DeviceArchiveStatus(deviceId, archived));
		return new ResponseEntity<>(NO_CONTENT);
	}


	@PutMapping("/devices/{deviceId}/name")
	public ResponseEntity<String> updateDeviceName(
		@PathVariable("deviceId") int deviceId,
		@RequestParam("name") String name) throws BusinessException {
		updateDeviceNameUseCase.updateDeviceName(new TinyDevice(deviceId, name));
		return new ResponseEntity<>(NO_CONTENT);
	}
}