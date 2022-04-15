package it.deltax.produlytics.api.detections.adapters;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.limits.LimitsInfo;
import it.deltax.produlytics.api.detections.business.domain.limits.MeanStddev;
import it.deltax.produlytics.api.detections.business.domain.limits.TechnicalLimits;
import it.deltax.produlytics.api.detections.business.domain.validate.CharacteristicInfo;
import it.deltax.produlytics.api.detections.business.domain.validate.DeviceInfo;
import it.deltax.produlytics.api.detections.business.ports.out.*;
import it.deltax.produlytics.api.repositories.CharacteristicRepository;
import it.deltax.produlytics.api.repositories.DetectionRepository;
import it.deltax.produlytics.api.repositories.DeviceRepository;
import it.deltax.produlytics.api.repositories.LimitsEntity;
import it.deltax.produlytics.persistence.DetectionEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@SuppressWarnings("unused")
public class DetectionsAdapter implements FindDeviceByApiKeyPort,
	FindCharacteristicByNamePort,
	FindLastDetectionsPort,
	InsertDetectionPort,
	FindLimitsPort,
	MarkOutlierPort
{
	private final CharacteristicRepository characteristicRepository;
	private final DeviceRepository deviceRepository;
	private final DetectionRepository detectionRepository;

	public DetectionsAdapter(
		CharacteristicRepository characteristicRepository,
		DeviceRepository deviceRepository,
		DetectionRepository detectionRepository
	) {
		this.characteristicRepository = characteristicRepository;
		this.deviceRepository = deviceRepository;
		this.detectionRepository = detectionRepository;
	}

	@Override
	public Optional<DeviceInfo> findDeviceByApiKey(String apiKey) {
		return this.deviceRepository.findByApiKey(apiKey)
			.map(deviceEntity -> new DeviceInfo(deviceEntity.getId(),
				deviceEntity.getArchived(),
				deviceEntity.getDeactivated()
			));
	}

	@Override
	public Optional<CharacteristicInfo> findCharacteristicByName(int deviceId, String name) {
		return this.characteristicRepository.findByDeviceIdAndName(deviceId, name)
			.map(characteristicEntity -> new CharacteristicInfo(characteristicEntity.getId(),
				characteristicEntity.getArchived()
			));
	}

	@Override
	public List<Detection> findLastDetections(CharacteristicId characteristicId, int count) {
		return this.detectionRepository.findLastDetectionsById(characteristicId.deviceId(),
				characteristicId.characteristicId(),
				count
			)
			.stream()
			.map(detectionEntity -> new Detection(
				new CharacteristicId(detectionEntity.getDeviceId(), detectionEntity.getCharacteristicId()),
				detectionEntity.getCreationTime(),
				detectionEntity.getValue()
			))
			.toList();
	}

	@Override
	public void insertDetection(Detection detection) {
		DetectionEntity detectionEntity = new DetectionEntity(detection.creationTime(),
			detection.characteristicId().characteristicId(),
			detection.characteristicId().deviceId(),
			detection.value(),
			false
		);
		this.detectionRepository.save(detectionEntity);
	}

	@Override
	public LimitsInfo findLimits(CharacteristicId characteristicId) {
		LimitsEntity limitsEntity = this.characteristicRepository.findLimits(characteristicId.deviceId(),
			characteristicId.characteristicId()
		);

		Optional<TechnicalLimits> technicalLimits = Optional.empty();
		if(limitsEntity.getTechnicalLowerLimit().isPresent() && limitsEntity.getTechnicalUpperLimit().isPresent()) {
			double lowerLimit = limitsEntity.getTechnicalLowerLimit().get();
			double upperLimit = limitsEntity.getTechnicalUpperLimit().get();
			technicalLimits = Optional.of(new TechnicalLimits(lowerLimit, upperLimit));
		}

		Optional<MeanStddev> meanStddev = Optional.empty();
		if(limitsEntity.getAutoAdjust()) {
			double mean = limitsEntity.getComputedMean();
			double stddev = limitsEntity.getComputedStddev();
			meanStddev = Optional.of(new MeanStddev(mean, stddev));
		}

		return new LimitsInfo(technicalLimits, meanStddev);
	}

	@Override
	public void markOutlier(Detection detection) {
		this.detectionRepository.markOutlier(detection.characteristicId().deviceId(),
			detection.characteristicId().characteristicId(),
			detection.creationTime()
		);
	}
}
