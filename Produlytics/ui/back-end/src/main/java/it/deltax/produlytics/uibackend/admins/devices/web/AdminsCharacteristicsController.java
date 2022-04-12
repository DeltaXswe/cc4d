package it.deltax.produlytics.uibackend.admins.devices.web;

import com.fasterxml.jackson.databind.JsonNode;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.Characteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.CharacteristicArchiveStatus;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.GetCharacteristicsUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.InsertCharacteristicUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.UpdateCharacteristicArchiveStatusUseCase;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Il controller per le richieste effettuate dagli amministratori relative alle caratteristiche
 * @author Alberto Lazari
 */
@RestController
@RequestMapping("/admins/devices/{deviceId}/characteristics")
public class AdminsCharacteristicsController {
	private final InsertCharacteristicUseCase insertCharacteristic;
	private final GetCharacteristicsUseCase getCharacteristics;
	private final UpdateCharacteristicArchiveStatusUseCase updateCharacteristicArchiveStatus;

	/**
	 * Il costruttore
	 * @param insertCharacteristic l'interfaccia per il caso d'uso d'inserimento di una caratteristica
	 * @param getCharacteristics   l'interfaccia per il caso d'uso di ottenimento della lista delle caratteristiche
	 *                             di una macchina
	 */
	AdminsCharacteristicsController(
		InsertCharacteristicUseCase insertCharacteristic,
		GetCharacteristicsUseCase getCharacteristics,
		UpdateCharacteristicArchiveStatusUseCase updateCharacteristicArchiveStatus
	) {
		this.insertCharacteristic = insertCharacteristic;
		this.getCharacteristics = getCharacteristics;
		this.updateCharacteristicArchiveStatus = updateCharacteristicArchiveStatus;
	}

	/**
	 * Inserisce una nuova caratteristica nella macchina specificata
	 * @param deviceId id della macchina in cui inserire la caratteristica
	 * @param characteristic informazioni della nuova caratteristica
	 * @return l'id della caratteristica creata
	 * @throws BusinessException se la caratteristica esiste già o la macchina è inesistente
	 */
	@PostMapping("")
	public ResponseEntity<Map<String, Integer>> insertCharacteristic(
		@PathVariable("deviceId") int deviceId,
		@RequestBody(required = false) NewCharacteristic characteristic
	) throws BusinessException {
		return ResponseEntity.ok(Map.of(
			"id",
			this.insertCharacteristic.insertByDevice(deviceId, characteristic)
		));
	}

	/**
	 * Riceve le chiamate all'endpoint REST per l'ottenimento di tutte le caratteristiche di una macchina
	 * @param deviceId l'id della macchina
	 * @return la lista delle caratteristiche della macchina
	 * @throws BusinessException se la macchina è inesistente
	 */
	@GetMapping("")
	public List<Characteristic> getCharacteristics(@PathVariable("deviceId") int deviceId) throws BusinessException {
		return this.getCharacteristics.getByDevice(deviceId);
	}

	/**
	 * Riceve le chiamate all'endpoint REST per la modifica dello stato di archiviazione di una caratteristica
	 * @param deviceId l'id della macchina
	 * @param characteristicId l'id della caratteristica da modifica
	 * @param body il corpo della richiesta HTTP
	 * @return lo stato HTTP
	 * @throws BusinessException se la caratteristica è inesistente
	 */
	@PutMapping("/{characteristicId}/archived")
	public ResponseEntity<String> updateCharacteristicArchiveStatus(
		@PathVariable("deviceId") int deviceId,
		@PathVariable("characteristicId") int characteristicId,
		@RequestBody JsonNode body
	) throws BusinessException {
		boolean archived = body.get("archived").asBoolean();
		this.updateCharacteristicArchiveStatus.updateCharacteristicArchiveStatus(new CharacteristicArchiveStatus(
			characteristicId,
			deviceId,
			archived
		));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
