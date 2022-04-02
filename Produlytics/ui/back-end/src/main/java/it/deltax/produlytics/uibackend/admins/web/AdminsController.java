package it.deltax.produlytics.uibackend.admins.web;

import it.deltax.produlytics.uibackend.accounts.business.domain.AccountArchiveStatus;
import it.deltax.produlytics.uibackend.accounts.business.domain.AccountTiny;
import it.deltax.produlytics.uibackend.admins.business.domain.DeviceDeactivateStatus;
import it.deltax.produlytics.uibackend.admins.business.domain.UpdateAdminAccout;
import it.deltax.produlytics.uibackend.admins.business.domain.DeviceArchiveStatus;
import it.deltax.produlytics.uibackend.admins.business.domain.InsertAccount;
import it.deltax.produlytics.uibackend.admins.business.ports.in.*;
import it.deltax.produlytics.uibackend.devices.business.domain.Device;
import it.deltax.produlytics.uibackend.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import static org.springframework.http.HttpStatus.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@RestController
@RequestMapping("/admin")
public class AdminsController {
	private final UpdateAccountByAdminUseCase updateAccountByAdminUseCase;
	private final UpdateAccountArchiveStatusUseCase updateAccountArchiveStatusUseCase;
	private final InsertAccountUseCase insertAccountUseCase;
	private final UpdateDeviceNameUseCase updateDeviceNameUseCase;
	private final UpdateDeviceArchiveStatusUseCase updateDeviceArchiveStatusUseCase;
	private final UpdateDeviceDeactivateStatusUseCase updateDeviceDeativateStatusUseCase;
	private final GetDevicesUseCase getDevicesUseCase;
	private final GetDeviceDetailsUseCase getDeviceDetailsUseCase;
	private final GetAccountsUseCase getAccountsUseCase;

	public AdminsController(
		UpdateAccountByAdminUseCase updateAccountByAdminUseCase,
		UpdateAccountArchiveStatusUseCase updateAccountArchiveStatusUseCase,
		InsertAccountUseCase insertAccountUseCase,
		UpdateDeviceNameUseCase updateDeviceNameUseCase,
		UpdateDeviceArchiveStatusUseCase updateDeviceArchiveStatusUseCase,
		UpdateDeviceDeactivateStatusUseCase updateDeviceDeativateStatusUseCase,
		GetDevicesUseCase getDevicesUseCase,
		GetDeviceDetailsUseCase getDeviceDetailsUseCase,
		GetAccountsUseCase getAccountsUseCase
	){
		this.updateAccountByAdminUseCase = updateAccountByAdminUseCase;
		this.updateAccountArchiveStatusUseCase = updateAccountArchiveStatusUseCase;
		this.insertAccountUseCase = insertAccountUseCase;
		this.updateDeviceNameUseCase = updateDeviceNameUseCase;
		this.updateDeviceArchiveStatusUseCase = updateDeviceArchiveStatusUseCase;
		this.updateDeviceDeativateStatusUseCase = updateDeviceDeativateStatusUseCase;
		this.getDevicesUseCase = getDevicesUseCase;
		this.getDeviceDetailsUseCase = getDeviceDetailsUseCase;
		this.getAccountsUseCase = getAccountsUseCase;
	}

	@PostMapping("/accounts")
	public Map<String, String> insertAccount(
		@RequestParam("username") String username,
		@RequestParam("password") String password,
		@RequestParam("administrator") boolean administrator) throws BusinessException {
		insertAccountUseCase.insertAccount(new InsertAccount(username, password, administrator));
		Map<String, String> map = new HashMap<>();
		map.put("username", username);
		return map;
	}

	@GetMapping("/accounts")
	public List<AccountTiny> getAccounts() throws BusinessException {
		return getAccountsUseCase.getAccounts();
	}

	@PutMapping("/{username}")
	public ResponseEntity<String> updateAccount(
		@PathVariable("username") String username,
		@RequestParam("newPassword") Optional<String> newPassword,
		@RequestParam("administrator") boolean administrator) throws BusinessException {
		updateAccountByAdminUseCase.updateByUsername(new UpdateAdminAccout(username, newPassword, administrator));
		return new ResponseEntity<>(NO_CONTENT);
	}

	@PutMapping("accounts/{username}/archived")
	public ResponseEntity<String> updateAccountArchiveStatus(
		@PathVariable("username") String username,
		@RequestParam("archived") boolean archived) throws BusinessException {
		updateAccountArchiveStatusUseCase.updateAccountArchiveStatus(new AccountArchiveStatus(username, archived));
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

	@GetMapping("/devices/{deviceId}")
	public Optional<DetailedDevice>  getDeviceDetails(
		@PathVariable("deviceId") int id) throws BusinessException {
		return getDeviceDetailsUseCase.getDeviceDetails(id);
	}


}