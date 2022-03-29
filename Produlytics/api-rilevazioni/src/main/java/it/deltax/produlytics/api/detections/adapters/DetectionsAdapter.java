package it.deltax.produlytics.api.detections.adapters;

import it.deltax.produlytics.api.detections.business.domain.*;
import it.deltax.produlytics.api.detections.business.domain.serie.LimitsInfo;
import it.deltax.produlytics.api.detections.business.domain.serie.MeanStddev;
import it.deltax.produlytics.api.detections.business.domain.serie.TechnicalLimits;
import it.deltax.produlytics.api.detections.business.domain.validate.CharacteristicInfo;
import it.deltax.produlytics.api.detections.business.domain.validate.DeviceInfo;
import it.deltax.produlytics.api.detections.business.ports.out.*;
import it.deltax.produlytics.api.repositories.CharacteristicRepository;
import it.deltax.produlytics.api.repositories.DetectionRepository;
import it.deltax.produlytics.api.repositories.DeviceRepository;
import it.deltax.produlytics.api.repositories.LimitsEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import it.deltax.produlytics.persistence.DetectionEntity;
import it.deltax.produlytics.persistence.DetectionEntityId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@SuppressWarnings("unused")
public class DetectionsAdapter implements FindDeviceInfoByApiKeyPort,
	FindCharacteristicInfoPort,
	FindLastDetectionsPort,
	InsertDetectionPort,
	FindLimitsPort,
	MarkOutlierPort
{
	@Autowired
	private CharacteristicRepository characteristicRepository;
	@Autowired
	private DeviceRepository deviceRepository;
	@Autowired
	private DetectionRepository detectionRepository;

	@Override
	public Optional<DeviceInfo> findDeviceByApiKey(String apiKey) {
		return this.deviceRepository.findByApiKey(apiKey)
			.map(deviceEntity -> new DeviceInfo(deviceEntity.getId(),
				deviceEntity.getArchived(),
				deviceEntity.getDeactivated()
			));
	}

	@Override
	public Optional<CharacteristicInfo> findCharacteristic(int deviceId, int characteristicId) {
		return this.characteristicRepository.findById(new CharacteristicEntityId(deviceId, characteristicId))
			.map(characteristicEntity -> new CharacteristicInfo(characteristicEntity.getArchived()));
	}

	@Override
	public List<Detection> findLastDetections(int deviceId, int characteristicId, int count) {
		return this.detectionRepository.findLastDetectionsById(deviceId, characteristicId, count)
			.stream()
			.map(detectionEntity -> new Detection(detectionEntity.getId().getDeviceId(),
				detectionEntity.getId().getCharacteristicId(),
				detectionEntity.getId().getCreationTime(),
				detectionEntity.getValue()
			))
			.toList();
	}

	@Override
	public void insertDetection(Detection detection) {
		DetectionEntityId detectionEntityId = new DetectionEntityId(detection.creationTime(),
			detection.characteristicId(),
			detection.deviceId()
		);
		DetectionEntity detectionEntity = new DetectionEntity(detectionEntityId, detection.value(), false);
		this.detectionRepository.save(detectionEntity);
	}

	@Override
	public LimitsInfo findLimits(int deviceId, int characteristicId) {
		LimitsEntity limitsEntity = this.characteristicRepository.findLimits(deviceId, characteristicId);

		Optional<TechnicalLimits> technicalLimits = Optional.empty();
		if(limitsEntity.getTechnicalLowerLimit().isPresent() && limitsEntity.getTechnicalUpperLimit().isPresent()) {
			double lowerLimit = limitsEntity.getTechnicalLowerLimit().get();
			double upperLimit = limitsEntity.getTechnicalUpperLimit().get();
			technicalLimits = Optional.of(new TechnicalLimits(lowerLimit, upperLimit));
		}

		Optional<MeanStddev> meanStddev = Optional.empty();
		if(limitsEntity.getComputedMean().isPresent() && limitsEntity.getComputedStddev().isPresent()) {
			double mean = limitsEntity.getComputedMean().get();
			double stddev = limitsEntity.getComputedStddev().get();
			meanStddev = Optional.of(new MeanStddev(mean, stddev));
		}

		return new LimitsInfo(technicalLimits, meanStddev);
	}

	@Override
	public void markOutlier(Detection detection) {
		this.detectionRepository.markOutlier(detection.deviceId(),
			detection.characteristicId(),
			detection.creationTime()
		);
	}
}
