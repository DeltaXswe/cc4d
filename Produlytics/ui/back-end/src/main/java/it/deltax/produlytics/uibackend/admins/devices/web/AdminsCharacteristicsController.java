package it.deltax.produlytics.uibackend.admins.devices.web;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.Characteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.GetCharacteristicsUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.InsertCharacteristicUseCase;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
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

	/**
	 * Il costruttore
	 * @param insertCharacteristic l'interfaccia per il caso d'uso d'inserimento di una caratteristica
	 * @param getCharacteristics   l'interfaccia per il caso d'uso di ottenimento della lista delle caratteristiche
	 *                             di una macchina
	 */
	AdminsCharacteristicsController(
		InsertCharacteristicUseCase insertCharacteristic,
		GetCharacteristicsUseCase getCharacteristics
	) {
		this.insertCharacteristic = insertCharacteristic;
		this.getCharacteristics = getCharacteristics;
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
}
