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
import it.deltax.produlytics.api.repositories.MeanStddevEntity;
import it.deltax.produlytics.persistence.CharacteristicEntity;
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
   * @param characteristicRepository una repository di caratteristiche
   * @param deviceRepository una repository di macchine
   * @param detectionRepository una repository di rilevazioni
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
   * Implementa l'omonimo metodo definito in {@code FindDeviceByApiKeyPort}.
   *
   * @param apiKey la chiave API della macchina da cercare, la quale potrebbe non esistere
   * @return le informazioni della macchina se esiste, altrimenti {@code Optional.empty()}
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
   * Implementa l'omonimo metodo definito in {@code FindCharacteristicByNamePort}.
   *
   * @param deviceId l'identificativo della macchina, che deve esistere, a cui appartiene la
   *     caratteristica da cercare
   * @param name il nome della caratteristica da cercare
   * @return le informazioni della caratteristica se esite, altrimenti {@code Optional.empty()}
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
   * Implementa l'omonimo metodo definito in {@code FindLastDetectionsPort}.
   *
   * @param characteristicId l'identificativo globale della caratteristica a cui appartengono le
   *     rilevazioni da ottenere. Si può assumere che esista una caratteristica con tale
   *     identificativo
   * @param count il numero di rilevazioni da ottenere
   * @return una lista delle ultime {@code count} rilevazioni della caratteristica con
   *     identificativo `characteristicId, o meno se non ce ne sono abbastanza
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
   * Implementa l'omonimo metodo definito in {@code InsertDetectionPort}.
   *
   * @param detection la rilevazione da memorizzare
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
   * Implementa l'omonimo metodo definito in {@code FindLimitsPort}.
   *
   * @param characteristicId l'identificativo globale della caratteristica a cui appartengono i
   *     limiti da ottenere. Si può assumere che esista una caratteristica con tale identificativo
   * @return i limiti tecnici e di processo della caratteristica cercata
   */
  @Override
  public LimitsInfo findLimits(CharacteristicId characteristicId) {
    CharacteristicEntity characteristicEntity =
        this.characteristicRepository.findByDeviceIdAndId(
            characteristicId.deviceId(), characteristicId.characteristicId());

    Optional<TechnicalLimits> technicalLimits = Optional.empty();
    if (characteristicEntity.getLowerLimit() != null
        && characteristicEntity.getUpperLimit() != null) {
      double lowerLimit = characteristicEntity.getLowerLimit();
      double upperLimit = characteristicEntity.getUpperLimit();
      technicalLimits = Optional.of(new TechnicalLimits(lowerLimit, upperLimit));
    }

    Optional<MeanStddev> meanStddev = Optional.empty();
    if (characteristicEntity.getAutoAdjust()) {
      MeanStddevEntity meanStddevEntity;
      if (characteristicEntity.getSampleSize() != null) {
        meanStddevEntity =
            this.characteristicRepository.meanStddevWithSampleSize(
                characteristicId.deviceId(),
                characteristicId.characteristicId(),
                characteristicEntity.getSampleSize());
      } else {
        meanStddevEntity =
            this.characteristicRepository.meanStddevWithoutSampleSize(
                characteristicId.deviceId(), characteristicId.characteristicId());
      }
      double mean = meanStddevEntity.getMean();
      double stddev = meanStddevEntity.getStddev();
      meanStddev = Optional.of(new MeanStddev(mean, stddev));
    }

    return new LimitsInfo(technicalLimits, meanStddev);
  }

  /**
   * Implementa l'omonimo metodo definito in {@code MarkOutlierPort}.
   *
   * @param detection la rilevazione da marcare come anomala
   */
  @Override
  public void markOutlier(Detection detection) {
    this.detectionRepository.markOutlier(
        detection.characteristicId().deviceId(),
        detection.characteristicId().characteristicId(),
        detection.creationTime());
  }
}
