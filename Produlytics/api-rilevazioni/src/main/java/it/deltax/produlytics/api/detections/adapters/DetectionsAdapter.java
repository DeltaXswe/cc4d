package it.deltax.produlytics.api.detections.adapters;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.limits.LimitsInfo;
import it.deltax.produlytics.api.detections.business.domain.limits.MeanStddev;
import it.deltax.produlytics.api.detections.business.domain.limits.TechnicalLimits;
import it.deltax.produlytics.api.detections.business.domain.validate.CharacteristicInfo;
import it.deltax.produlytics.api.detections.business.domain.validate.DeviceInfo;
import it.deltax.produlytics.api.detections.business.ports.out.FindCharacteristicByNamePort;
import it.deltax.produlytics.api.detections.business.ports.out.FindDeviceByApiKeyPort;
import it.deltax.produlytics.api.detections.business.ports.out.FindLastDetectionsPort;
import it.deltax.produlytics.api.detections.business.ports.out.FindLimitsPort;
import it.deltax.produlytics.api.detections.business.ports.out.InsertDetectionPort;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;
import it.deltax.produlytics.api.repositories.CharacteristicRepository;
import it.deltax.produlytics.api.repositories.DetectionRepository;
import it.deltax.produlytics.api.repositories.DeviceRepository;
import it.deltax.produlytics.api.repositories.LimitsEntity;
import it.deltax.produlytics.persistence.DetectionEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * Questa classe si occupa di adattare i repository di Spring alle porte descritte dalla parte di
 * business.
 */
@Component
@SuppressWarnings("unused")
public class DetectionsAdapter
    implements FindDeviceByApiKeyPort,
        FindCharacteristicByNamePort,
        FindLastDetectionsPort,
        InsertDetectionPort,
        FindLimitsPort,
        MarkOutlierPort {
  /** Un'istanza del repository delle caratteristiche. */
  private final CharacteristicRepository characteristicRepository;
  /** Un'istanza del repository delle macchine. */
  private final DeviceRepository deviceRepository;
  /** Un'istanza del repository delle rilevazioni. */
  private final DetectionRepository detectionRepository;

  /**
   * Crea una nuova istanza di DetectionsAdapter.
   *
   * @param characteristicRepository Una repository di caratteristiche.
   * @param deviceRepository Una repository di macchine.
   * @param detectionRepository Una repository di rilevazioni.
   */
  public DetectionsAdapter(
      CharacteristicRepository characteristicRepository,
      DeviceRepository deviceRepository,
      DetectionRepository detectionRepository) {
    this.characteristicRepository = characteristicRepository;
    this.deviceRepository = deviceRepository;
    this.detectionRepository = detectionRepository;
  }

  /**
   * Implementa l'omonimo metodo definito in `FindDeviceByApiKeyPort`.
   *
   * @param apiKey La chiave API della macchina da cercare, la quale potrebbe non esistere.
   * @return Le informazioni della macchina se esiste, altrimenti `Optional.empty()`.
   */
  @Override
  public Optional<DeviceInfo> findDeviceByApiKey(String apiKey) {
    return this.deviceRepository
        .findByApiKey(apiKey)
        .map(
            deviceEntity ->
                new DeviceInfo(
                    deviceEntity.getId(),
                    deviceEntity.getArchived(),
                    deviceEntity.getDeactivated()));
  }

  /**
   * Implementa l'omonimo metodo definito in `FindCharacteristicByNamePort`.
   *
   * @param deviceId L'identificativo della macchina, che deve esistere, a cui appartiene la
   *     caratteristica da cercare.
   * @param name Il nome della caratteristica da cercare.
   * @return Le informazioni della caratteristica se esite, altrimenti `Optional.empty()`.
   */
  @Override
  public Optional<CharacteristicInfo> findCharacteristicByName(int deviceId, String name) {
    return this.characteristicRepository
        .findByDeviceIdAndName(deviceId, name)
        .map(
            characteristicEntity ->
                new CharacteristicInfo(
                    characteristicEntity.getId(), characteristicEntity.getArchived()));
  }

  /**
   * Implementa l'omonimo metodo definito in `FindLastDetectionsPort`.
   *
   * @param characteristicId L'identificativo globale della caratteristica a cui appartengono le
   *     rilevazioni da ottenere. Si può assumere che esista una caratteristica con tale
   *     identificativo.
   * @param count Il numero di rilevazioni da ottenere.
   * @return Una lista delle ultime `count` rilevazioni della caratteristica con identificativo
   *     `characteristicId, o meno se non ce ne sono abbastanza.
   */
  @Override
  public List<Detection> findLastDetections(CharacteristicId characteristicId, int count) {
    return this.detectionRepository
        .findLastDetectionsById(
            characteristicId.deviceId(), characteristicId.characteristicId(), count)
        .stream()
        .map(
            detectionEntity ->
                new Detection(
                    new CharacteristicId(
                        detectionEntity.getDeviceId(), detectionEntity.getCharacteristicId()),
                    detectionEntity.getCreationTime(),
                    detectionEntity.getValue()))
        .toList();
  }

  /**
   * Implementa l'omonimo metodo definito in `InsertDetectionPort`.
   *
   * @param detection La rilevazione da memorizzare.
   */
  @Override
  public void insertDetection(Detection detection) {
    DetectionEntity detectionEntity =
        new DetectionEntity(
            detection.creationTime(),
            detection.characteristicId().characteristicId(),
            detection.characteristicId().deviceId(),
            detection.value(),
            false);
    this.detectionRepository.save(detectionEntity);
  }

  /**
   * Implementa l'omonimo metodo definito in `FindLimitsPort`.
   *
   * @param characteristicId L'identificativo globale della caratteristica a cui appartengono i
   *     limiti da ottenere. Si può assumere che esista una caratteristica con tale identificativo.
   * @return I limiti tecnici e di processo della caratteristica cercata.
   */
  @Override
  public LimitsInfo findLimits(CharacteristicId characteristicId) {
    LimitsEntity limitsEntity =
        this.characteristicRepository.findLimits(
            characteristicId.deviceId(), characteristicId.characteristicId());

    Optional<TechnicalLimits> technicalLimits = Optional.empty();
    if (limitsEntity.getTechnicalLowerLimit().isPresent()
        && limitsEntity.getTechnicalUpperLimit().isPresent()) {
      double lowerLimit = limitsEntity.getTechnicalLowerLimit().get();
      double upperLimit = limitsEntity.getTechnicalUpperLimit().get();
      technicalLimits = Optional.of(new TechnicalLimits(lowerLimit, upperLimit));
    }

    Optional<MeanStddev> meanStddev = Optional.empty();
    if (limitsEntity.getAutoAdjust()) {
      double mean = limitsEntity.getComputedMean();
      double stddev = limitsEntity.getComputedStddev();
      meanStddev = Optional.of(new MeanStddev(mean, stddev));
    }

    return new LimitsInfo(technicalLimits, meanStddev);
  }

  /**
   * Implementa l'omonimo metodo definito in `MarkOutlierPort`.
   *
   * @param detection La rilevazione da marcare come anomala.
   */
  @Override
  public void markOutlier(Detection detection) {
    this.detectionRepository.markOutlier(
        detection.characteristicId().deviceId(),
        detection.characteristicId().characteristicId(),
        detection.creationTime());
  }
}
