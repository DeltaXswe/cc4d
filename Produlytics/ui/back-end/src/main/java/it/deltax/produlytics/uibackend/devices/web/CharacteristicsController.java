package it.deltax.produlytics.uibackend.devices.web;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLimits;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyCharacteristic;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetLimitsUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetUnarchivedCharacteristicsUseCase;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Il controller per le richieste effettuate relative alle caratteristiche non archiviate di una
 * macchina non archiviata.
 */
@RestController
@RequestMapping("/devices/{deviceId}/characteristics")
public class CharacteristicsController {
  private final GetUnarchivedCharacteristicsUseCase getUnarchivedCharacteristicsUseCase;
  private final GetLimitsUseCase getLimitsUseCase;

  /**
   * Il costruttore.
   *
   * @param getUnarchivedCharacteristicsUseCase l'interfaccia per il caso d'uso di ottenimento delle
   *     caratteristiche non archiviate
   * @param getLimitsUseCase l'interfaccia per il caso d'uso di ottenimento dei limiti tecnici di
   *     una caratteristica non archiviata
   */
  public CharacteristicsController(
      GetUnarchivedCharacteristicsUseCase getUnarchivedCharacteristicsUseCase,
      GetLimitsUseCase getLimitsUseCase) {
    this.getUnarchivedCharacteristicsUseCase = getUnarchivedCharacteristicsUseCase;
    this.getLimitsUseCase = getLimitsUseCase;
  }

  /**
   * Riceve le chiamate all'endpoint REST per l'ottenimento delle caratteristiche non archiviate di
   * una macchina non archiviata.
   *
   * @param deviceId l'id della macchina
   * @return la lista delle caratteristiche non archiviate trovate
   * @throws BusinessException se la macchina ?? inesistente o archiviata
   */
  @GetMapping("")
  ResponseEntity<List<TinyCharacteristic>> getUnarchivedCharacteristics(
      @PathVariable("deviceId") int deviceId) throws BusinessException {
    return ResponseEntity.ok(this.getUnarchivedCharacteristicsUseCase.getByDevice(deviceId));
  }

  /**
   * Riceve le chiamate all'endpoint REST per l'ottenimento dei limiti di una caratteristica non
   * archiviata.
   *
   * @param deviceId l'id della macchina
   * @param characteristicId l'id della caratteristica
   * @return i limiti tecnici della caratteristica
   * @throws BusinessException se la caratteristica ?? inesistente o ?? archiviata
   */
  @GetMapping("{characteristicId}/limits")
  ResponseEntity<CharacteristicLimits> getCharacteristicLimits(
      @PathVariable("deviceId") int deviceId,
      @PathVariable("characteristicId") int characteristicId)
      throws BusinessException {
    return ResponseEntity.ok(this.getLimitsUseCase.getByCharacteristic(deviceId, characteristicId));
  }
}
