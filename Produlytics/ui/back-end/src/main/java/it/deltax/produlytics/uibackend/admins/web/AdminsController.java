package it.deltax.produlytics.uibackend.admins.web;

import it.deltax.produlytics.uibackend.admins.business.domain.DeviceDeactivateStatus;
import it.deltax.produlytics.uibackend.admins.business.domain.UpdateAdminAccout;
import it.deltax.produlytics.uibackend.admins.business.domain.DeviceArchiveStatus;
import it.deltax.produlytics.uibackend.admins.business.domain.InsertAccount;
import it.deltax.produlytics.uibackend.admins.business.ports.in.*;
import it.deltax.produlytics.uibackend.devices.business.domain.Device;
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
	private final UpdateDeviceDeactivateStatusUseCase updateDeviceDeativateStatusUseCase;
	private final GetDevicesUseCase getDevicesUseCase;

	public AdminsController(
		UpdateAccountByAdminUseCase updateAccountByAdminUseCase,
		InsertAccountUseCase insertAccountUseCase,
		UpdateDeviceNameUseCase updateDeviceNameUseCase,
		UpdateDeviceArchiveStatusUseCase updateDeviceArchiveStatusUseCase,
		UpdateDeviceDeactivateStatusUseCase updateDeviceDeativateStatusUseCase,
		GetDevicesUseCase getDevicesUseCase
	){
		this.updateAccountByAdminUseCase = updateAccountByAdminUseCase;
		this.insertAccountUseCase = insertAccountUseCase;
		this.updateDeviceNameUseCase = updateDeviceNameUseCase;
		this.updateDeviceArchiveStatusUseCase = updateDeviceArchiveStatusUseCase;
		this.updateDeviceDeativateStatusUseCase = updateDeviceDeativateStatusUseCase;
		this.getDevicesUseCase = getDevicesUseCase;
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

	@GetMapping("/devices")
	public Iterable<Device> getDevices() throws BusinessException {
		return getDevicesUseCase.getDevices();
	}

	@PutMapping("/devices/{id}/name")
	public ResponseEntity<String> updateDeviceName(
		@PathVariable("id") int id,
		@RequestParam("name") String name) throws BusinessException {
		updateDeviceNameUseCase.updateDeviceName(new TinyDevice(id, name));
		return new ResponseEntity<>(NO_CONTENT);
	}

	@PutMapping("devices/{id}/archived")
	public ResponseEntity<String> updateDeviceArchiveStatus(
		@PathVariable("id") int id,
		@RequestParam("archived") boolean archived) throws BusinessException {
		updateDeviceArchiveStatusUseCase.updateDeviceArchiveStatus(new DeviceArchiveStatus(id, archived));
		return new ResponseEntity<>(NO_CONTENT);
	}

	@PutMapping("/devices/{id}/deactivated")
	public ResponseEntity<String> updateDeviceDeactivateStatus(
		@PathVariable("id") int id,
		@RequestParam("deactivated") boolean deactivated) throws BusinessException {
		updateDeviceDeativateStatusUseCase.updateDeviceDeactivateStatus(new DeviceDeactivateStatus(id, deactivated));
		return new ResponseEntity<>(NO_CONTENT);
	}

}