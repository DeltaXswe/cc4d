package it.deltax.produlytics.api.detections.adapters;

import it.deltax.produlytics.api.detections.business.domain.*;
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
	FindLimitsPort
{
	@Autowired
	private CharacteristicRepository characteristicRepository;
	@Autowired
	private DeviceRepository deviceRepository;
	@Autowired
	private DetectionRepository detectionRepository;

	@Override
	public Optional<DeviceInfo> findDeviceByApiKey(
		String apiKey
	) {
		return this.deviceRepository.findByApiKey(apiKey)
			.map(deviceEntity -> new DeviceInfo(deviceEntity.getId(),
				deviceEntity.getArchived(),
				deviceEntity.getDeactivated()
			));
	}

	@Override
	public Optional<CharacteristicInfo> findCharacteristic(
		int deviceId, int characteristicId
	) {
		return this.characteristicRepository.findById(new CharacteristicEntityId(deviceId, characteristicId))
			.map(characteristicEntity -> new CharacteristicInfo(characteristicEntity.getArchived()));
	}

	@Override
	public List<MarkableDetection> findLastDetections(int deviceId, int characteristicId, int count) {
		return this.detectionRepository.findLastNById(deviceId, characteristicId, count)
			.stream()
			.map(detectionEntity -> (MarkableDetection) new MarkableDetectionAdapter(this.detectionRepository,
				detectionEntity.getId().getDeviceId(),
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
		if(limitsEntity.technicalLowerLimit().isPresent() && limitsEntity.technicalUpperLimit().isPresent()) {
			double lowerLimit = limitsEntity.technicalLowerLimit().get();
			double upperLimit = limitsEntity.technicalUpperLimit().get();
			technicalLimits = Optional.of(new TechnicalLimits(lowerLimit, upperLimit));
		}

		Optional<MeanStddev> meanStddev = Optional.empty();
		if(limitsEntity.computedMean().isPresent() && limitsEntity.computedStddev().isPresent()) {
			double mean = limitsEntity.computedMean().get();
			double stddev = limitsEntity.computedStddev().get();
			meanStddev = Optional.of(new MeanStddev(mean, stddev));
		}

		return new LimitsInfo(technicalLimits, meanStddev);
	}
}
