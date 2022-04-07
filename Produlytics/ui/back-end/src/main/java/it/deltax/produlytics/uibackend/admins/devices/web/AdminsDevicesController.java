package it.deltax.produlytics.uibackend.admins.devices.web;

import com.fasterxml.jackson.databind.JsonNode;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceArchiveStatus;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceDeactivateStatus;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceToInsert;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.*;
import it.deltax.produlytics.uibackend.devices.business.domain.*;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminsDevicesController {
	private final InsertDeviceUseCase insertDeviceUseCase;
	private final GetDevicesUseCase getDevicesUseCase;
	private final GetDeviceDetailsUseCase getDeviceDetailsUseCase;
	private final UpdateDeviceNameUseCase updateDeviceNameUseCase;
	private final UpdateDeviceArchiveStatusUseCase updateDeviceArchiveStatusUseCase;
	private final UpdateDeviceDeactivateStatusUseCase updateDeviceDeactivateStatusUseCase;

	public AdminsDevicesController(
		InsertDeviceUseCase insertDeviceUseCase,
		GetDevicesUseCase getDevicesUseCase,
		GetDeviceDetailsUseCase getDeviceDetailsUseCase,
		UpdateDeviceNameUseCase updateDeviceNameUseCase,
		UpdateDeviceArchiveStatusUseCase updateDeviceArchiveStatusUseCase,
		UpdateDeviceDeactivateStatusUseCase updateDeviceDeativateStatusUseCase
	){

		this.insertDeviceUseCase = insertDeviceUseCase;
		this.getDevicesUseCase = getDevicesUseCase;
		this.getDeviceDetailsUseCase = getDeviceDetailsUseCase;
		this.updateDeviceNameUseCase = updateDeviceNameUseCase;
		this.updateDeviceArchiveStatusUseCase = updateDeviceArchiveStatusUseCase;
		this.updateDeviceDeactivateStatusUseCase = updateDeviceDeativateStatusUseCase;
	}


	@PostMapping("/devices")
	public ResponseEntity<Map<String, String>> insertDevice(
		@RequestBody DeviceToInsert device) throws BusinessException {
		int id = insertDeviceUseCase.insertDevice(device);
		Map<String, String> map = new HashMap<>();
		map.put("id", String.valueOf(id));
		return ResponseEntity.ok(map);
	}

	@GetMapping("/devices")
	public ResponseEntity<Iterable<Device>> getDevices() throws BusinessException {
		return ResponseEntity.ok(getDevicesUseCase.getDevices());
	}

	@GetMapping("/devices/{id}")
	public ResponseEntity<Optional<DetailedDevice>> getDeviceDetails(
		@PathVariable("id") int id) throws BusinessException {
		return ResponseEntity.ok(getDeviceDetailsUseCase.getDeviceDetails(id));
	}

	@PutMapping("/devices/{id}/name")
	public ResponseEntity<String> updateDeviceName(
		@PathVariable("id") int id,
		@RequestBody JsonNode body) throws BusinessException {
		String name = body.get("name").toString();
		updateDeviceNameUseCase.updateDeviceName(new TinyDevice(id, name));
		return new ResponseEntity<>(NO_CONTENT);
	}

	@PutMapping("devices/{id}/archived")
	public ResponseEntity<String> updateDeviceArchiveStatus(
		@PathVariable("id") int id,
		@RequestBody JsonNode body) throws BusinessException {
		boolean archived = body.get("archived").asBoolean();
		updateDeviceArchiveStatusUseCase.updateDeviceArchiveStatus(new DeviceArchiveStatus(id, archived));
		return new ResponseEntity<>(NO_CONTENT);
	}

	@PutMapping("/devices/{id}/deactivated")
	public ResponseEntity<String> updateDeviceDeactivateStatus(
		@PathVariable("id") int id,
		@RequestBody JsonNode body) throws BusinessException {
		boolean deactivated = body.get("deactivated").asBoolean();
		updateDeviceDeactivateStatusUseCase.updateDeviceDeactivateStatus(new DeviceDeactivateStatus(id, deactivated));
		return new ResponseEntity<>(NO_CONTENT);
	}


}
