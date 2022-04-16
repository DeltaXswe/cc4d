package it.deltax.produlytics.uibackend.admins.devices.web;

import com.fasterxml.jackson.databind.JsonNode;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.Characteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.CharacteristicArchiveStatus;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.CharacteristicToUpdate;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.GetCharacteristicsUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.InsertCharacteristicUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.UpdateCharacteristicArchiveStatusUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.UpdateCharacteristicUseCase;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.OptionalInt;

/**
 * Il controller per le richieste effettuate dagli amministratori, relative alle caratteristiche
 */
@RestController
@RequestMapping("/admin/devices/{deviceId}/characteristics")
public class AdminsCharacteristicsController {
	private final InsertCharacteristicUseCase insertCharacteristicUseCase;
	private final GetCharacteristicsUseCase getCharacteristicsUseCase;
	private final UpdateCharacteristicArchiveStatusUseCase updateCharacteristicArchiveStatusUseCase;
	private final UpdateCharacteristicUseCase updateCharacteristicUseCase;

	/**
	 * Il costruttore
	 * @param insertCharacteristicUseCase l'interfaccia per il caso d'uso d'inserimento di una caratteristica
	 * @param getCharacteristicsUseCase l'interfaccia per il caso d'uso di ottenimento della lista delle caratteristiche
	 *                              di una macchina
	 * @param updateCharacteristicArchiveStatusUseCase l'interfaccia per il caso d'uso di modifica dello stato di
	 *                                                    archiviazione della caratteristica di una macchina
	 * @param updateCharacteristicUseCase l'interfaccia per il caso d'uso di modifica della caratteristica di una
	 *                                      macchina
	 */
	AdminsCharacteristicsController(
		InsertCharacteristicUseCase insertCharacteristicUseCase,
		GetCharacteristicsUseCase getCharacteristicsUseCase,
		UpdateCharacteristicArchiveStatusUseCase updateCharacteristicArchiveStatusUseCase,
		UpdateCharacteristicUseCase updateCharacteristicUseCase
	) {
		this.insertCharacteristicUseCase = insertCharacteristicUseCase;
		this.getCharacteristicsUseCase = getCharacteristicsUseCase;
		this.updateCharacteristicArchiveStatusUseCase = updateCharacteristicArchiveStatusUseCase;
		this.updateCharacteristicUseCase = updateCharacteristicUseCase;
	}

	/**
	 * Riceve le chiamate all'endpoint REST per l'inserimento di una nuova caratteristica nella macchina specificata
	 * @param deviceId l'id della macchina
	 * @param characteristic le informazioni della nuova caratteristica
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
			this.insertCharacteristicUseCase.insertByDevice(deviceId, characteristic)
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
		return this.getCharacteristicsUseCase.getByDevice(deviceId);
	}

	/**
	 * Riceve le chiamate all'endpoint REST per la modifica dello stato di archiviazione della caratteristica di
	 * una macchina
	 * @param deviceId l'id della macchina
	 * @param characteristicId l'id della caratteristica da modificare
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
		this.updateCharacteristicArchiveStatusUseCase.updateCharacteristicArchiveStatus(new CharacteristicArchiveStatus(
			characteristicId,
			deviceId,
			archived
		));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	/**
	 * Riceve le chiamate all'endpoint REST per la modifica delle informazioni della caratteristica di una macchina
	 * @param deviceId l'id della macchina
	 * @param characteristicId l'id della caratteristica
	 * @param body il corpo della richiesta HTTP
	 * @return lo stato HTTP
	 * @throws BusinessException se le informazioni non sono valide o la caratteristica è inesistente
	 */
	@PutMapping("/{characteristicId}")
	public ResponseEntity<String> updateCharacteristic(
		@PathVariable("deviceId") int deviceId,
		@PathVariable("characteristicId") int characteristicId,
		@RequestBody JsonNode body
	) throws BusinessException {
		CharacteristicToUpdate.CharacteristicToUpdateBuilder toUpdateBuilder = CharacteristicToUpdate.builder()
			.withId(characteristicId)
			.withDeviceId(deviceId)
			.withName(body.get("name").asText())
			.withAutoAdjust(body.get("autoAdjust").asBoolean());

		if (body.hasNonNull("lowerLimit")) {
			toUpdateBuilder = toUpdateBuilder.withLowerLimit(OptionalDouble.of(body.get("lowerLimit").asDouble()));
		}

		if (body.hasNonNull("upperLimit")) {
			toUpdateBuilder = toUpdateBuilder.withUpperLimit(OptionalDouble.of(body.get("upperLimit").asDouble()));
		}

		if (body.hasNonNull("sampleSize")) {
			toUpdateBuilder = toUpdateBuilder.withSampleSize(OptionalInt.of(body.get("sampleSize").asInt()));
		}

		this.updateCharacteristicUseCase.updateCharacteristic(toUpdateBuilder.build());
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
