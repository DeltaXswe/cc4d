package it.deltax.produlytics.uibackend.devices.web;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLimits;
import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicTitle;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetLimitsUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedCharacteristicsUseCase;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Il controller per le richieste effettuate relative alle caratteristiche non archiviate di una macchina non archiviata
 */
@RestController
@RequestMapping("/devices/{id}/characteristics")
public class CharacteristicsController {
	private final GetUnarchivedCharacteristicsUseCase getUnarchivedCharacteristics;
	private final GetLimitsUseCase getLimits;

	/**
	 * Il costruttore
	 * @param getUnarchivedCharacteristics l'interfaccia per il caso d'uso di ottenimento delle caratteristiche non
	 *                                     archiviate
	 * @param getLimits l'interfaccia per il caso d'uso di ottenimento dei limiti tecnici di una caratteristica
	 */
	public CharacteristicsController(
		GetUnarchivedCharacteristicsUseCase getUnarchivedCharacteristics, GetLimitsUseCase getLimits
	) {
		this.getUnarchivedCharacteristics = getUnarchivedCharacteristics;
		this.getLimits = getLimits;
	}

	/**
	 * Riceve le chiamate all'endpoint REST per l'ottenimento delle caratteristiche non archiviate di una macchina non
	 * archiviata
	 * @param deviceId l'id della macchina
	 * @return la lista delle caratteristiche non archiviate trovate
	 * @throws BusinessException se la macchina è inesistente o archiviata
	 */
    @GetMapping("")
    ResponseEntity<List<CharacteristicTitle>> getUnarchivedCharacteristics(
        @PathVariable("id") int deviceId
    ) throws BusinessException {
        return ResponseEntity.ok(this.getUnarchivedCharacteristics.getByDevice(deviceId));
    }

	/**
	 * Riceve le chiamate all'endpoint REST per l'ottenimento dei limiti di una caratteristica non archiviata
	 * @param deviceId l'id della macchina
	 * @param characteristicId l'id della caratteristica
	 * @return i limiti tecnici della caratteristica
	 * @throws BusinessException se la caratteristica è inesistente o è archiviata
	 */
	@GetMapping("{characteristicId}/limits")
	ResponseEntity<CharacteristicLimits> getCharacteristicLimits(
        @PathVariable("id") int deviceId,
        @PathVariable("characteristicId") int characteristicId
    ) throws BusinessException {
        return ResponseEntity.ok(this.getLimits.getByCharacteristic(deviceId, characteristicId));
    }
}
