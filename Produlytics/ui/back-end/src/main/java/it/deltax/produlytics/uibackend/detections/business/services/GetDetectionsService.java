package it.deltax.produlytics.uibackend.detections.business.services;

import it.deltax.produlytics.uibackend.detections.business.domain.Detection;
import it.deltax.produlytics.uibackend.detections.business.domain.DetectionFilters;
import it.deltax.produlytics.uibackend.detections.business.domain.DetectionsGroup;
import it.deltax.produlytics.uibackend.detections.business.ports.in.GetDetectionsUseCase;
import it.deltax.produlytics.uibackend.detections.business.ports.out.FindAllDetectionsPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicLimitsPort;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalLong;

/** Il service per l'ottenimento della lista di rilevazioni */
public class GetDetectionsService implements GetDetectionsUseCase {
  private final FindAllDetectionsPort findDetectionsPort;
  private final FindCharacteristicLimitsPort findCharacteristicPort;

  /**
   * Il costruttore
   *
   * @param findDetectionsPort la porta per ottenere le rilevazioni
   * @param findCharacteristicPort la porta per ottenere i limiti di una caratteristica
   */
  public GetDetectionsService(
      FindAllDetectionsPort findDetectionsPort,
      FindCharacteristicLimitsPort findCharacteristicPort) {
    this.findDetectionsPort = findDetectionsPort;
    this.findCharacteristicPort = findCharacteristicPort;
  }

  /**
   * Restituisce la lista delle rilevazioni trovate della caratteristica non archiviata di una
   * macchina, applicando i filtri richiesti
   *
   * @param deviceId l'id della macchina
   * @param characteristicId l'id della caratteristica
   * @param filters i filtri di ricerca richiesti
   * @return la lisa di rilevazioni, con i timestamp utili per altre richieste
   * @throws BusinessException se la caratteristica è inesistente o è archiviata
   */
  @Override
  public DetectionsGroup listByCharacteristic(
      int deviceId, int characteristicId, DetectionFilters filters) throws BusinessException {
    this.findCharacteristicPort
        .findByCharacteristic(deviceId, characteristicId)
        .orElseThrow(() -> new BusinessException("characteristicNotFound", ErrorType.NOT_FOUND));

    List<Detection> detections =
        this.findDetectionsPort.findAllByCharacteristic(
            deviceId, characteristicId, filters.olderThan());
    final int initialSize = detections.size();

    if (filters.limit().isPresent()) {
      detections = detections.stream().limit(filters.limit().getAsInt()).toList();
    }

    if (filters.newerThan().isPresent()) {
      detections =
          detections.stream()
              .filter(detection -> detection.creationTime() > filters.newerThan().getAsLong())
              .toList();
    }

    if (detections.isEmpty())
      return new DetectionsGroup(
          Collections.emptyList(), OptionalLong.empty(), Instant.now().toEpochMilli());

    final long nextNew = detections.get(0).creationTime();
    final OptionalLong nextOld =
        detections.size() < initialSize
            ? OptionalLong.of(detections.get(detections.size() - 1).creationTime())
            : OptionalLong.empty();

    List<Detection> reversedDetections = new ArrayList<>(detections);
    Collections.reverse(reversedDetections);

    return new DetectionsGroup(reversedDetections, nextOld, nextNew);
  }
}
