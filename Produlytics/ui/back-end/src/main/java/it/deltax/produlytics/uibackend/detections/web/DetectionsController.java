package it.deltax.produlytics.uibackend.detections.web;

import it.deltax.produlytics.uibackend.detections.business.domain.DetectionFilters;
import it.deltax.produlytics.uibackend.detections.business.domain.Detections;
import it.deltax.produlytics.uibackend.detections.business.ports.in.GetDetectionsUseCase;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import org.springframework.web.bind.annotation.*;

import java.util.OptionalInt;
import java.util.OptionalLong;

/**
 * Il controller per le richieste relative alle rilevazioni
 */
@RestController
@RequestMapping("/devices/{deviceId}/characteristics/{characteristicId}/detections")
public class DetectionsController {
	private final GetDetectionsUseCase getDetectionsUseCase;

	/**
	 * Il costruttore
	 * @param getDetectionsUseCase l'interfaccia per il caso d'uso di ottenimento della lista delle
	 *                                       rilevazioni di una caratteristica
	 */
	public DetectionsController(GetDetectionsUseCase getDetectionsUseCase) {
		this.getDetectionsUseCase = getDetectionsUseCase;
	}

	/**
	 * Riceve le chiamate all'endpoint REST per l'ottenimento delle rilevazioni della caratteristica non archiviata
	 * di una macchina
	 * @param deviceId l'id della macchina
	 * @param characteristicId l'id della caratteristica
	 * @param olderThan il filtro che specifica che devono essere restituite rilevazioni più vecchie di un determinato
	 *                     istante. Può non essere specificato
	 * @param newerThan il filtro che specifica che devono essere restituite rilevazioni più recenti di un determinato
	 *                     istante. Può non essere specificato
	 * @param limit il filtro che specifica la lunghezza massima della lista di rilevazioni ricevuta. Può non essere
	 *              specificato
	 * @return la lista delle rilevazioni trovate, eventualmente applicando i filtri specificati
	 * @throws BusinessException se la caratteristica è inesistente
	 */
	@GetMapping("")
	public Detections getCharacteristicDetections(
		@PathVariable int deviceId,
		@PathVariable int characteristicId,
		@RequestParam(value = "olderThan", required = false) Long olderThan,
		@RequestParam(value = "newerThan", required = false) Long newerThan,
		@RequestParam(value = "limit", required = false) Integer limit
	) throws BusinessException {
		return this.getDetectionsUseCase.listByCharacteristic(
			deviceId, characteristicId, new DetectionFilters(
			olderThan != null ? OptionalLong.of(olderThan) : OptionalLong.empty(),
			newerThan != null ? OptionalLong.of(newerThan) : OptionalLong.empty(),
			limit != null ? OptionalInt.of(limit) : OptionalInt.empty()
		));
	}
}
