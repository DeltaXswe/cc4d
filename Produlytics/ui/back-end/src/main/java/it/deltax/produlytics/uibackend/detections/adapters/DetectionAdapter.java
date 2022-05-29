package it.deltax.produlytics.uibackend.detections.adapters;

import it.deltax.produlytics.uibackend.detections.business.domain.Detection;
import it.deltax.produlytics.uibackend.detections.business.ports.out.FindAllDetectionsPort;
import it.deltax.produlytics.uibackend.repositories.DetectionRepository;
import java.time.Instant;
import java.util.List;
import java.util.OptionalLong;
import org.springframework.stereotype.Component;

/** L'adapter dello strato di persistenza per le operazioni riguardanti le rilevazioni. */
@Component
public class DetectionAdapter implements FindAllDetectionsPort {
  private final DetectionRepository repo;

  /**
   * Il costruttore.
   *
   * @param repo lo strato di persistenza con i dati sulle rilevazioni
   */
  public DetectionAdapter(DetectionRepository repo) {
    this.repo = repo;
  }

  /**
   * Restituisce la lista di tutte le rilevazioni della caratteristica di una macchina,
   * eventualmente più vecchie di un istante specificato.
   *
   * @param deviceId l'id della macchina
   * @param characteristicId l'id della caratteristica
   * @param olderThan il filtro che specifica che devono essere restituite rilevazioni più vecchie
   *     di un determinato istante. Può non essere specificato
   * @return la lista delle rilevazioni trovate
   */
  @Override
  public List<Detection> findAllByCharacteristic(
      int deviceId, int characteristicId, OptionalLong olderThan) {
    return repo
        .findByCharacteristicAndCreationTimeGreaterThanQuery(
            deviceId,
            characteristicId,
            olderThan.isPresent()
                ? Instant.ofEpochMilli(olderThan.getAsLong())
                : Instant.now().plusSeconds(1))
        .stream()
        .map(
            detection ->
                new Detection(
                    detection.getCreationTime().toEpochMilli(),
                    detection.getValue(),
                    detection.getOutlier()))
        .toList();
  }
}
