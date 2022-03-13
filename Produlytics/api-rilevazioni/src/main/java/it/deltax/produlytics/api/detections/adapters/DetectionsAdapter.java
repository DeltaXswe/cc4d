package it.deltax.produlytics.api.detections.adapters;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.LimitsInfo;
import it.deltax.produlytics.api.detections.business.domain.validate.CharacteristicInfo;
import it.deltax.produlytics.api.detections.business.domain.validate.DeviceInfo;
import it.deltax.produlytics.api.detections.business.ports.out.*;
import it.deltax.produlytics.api.repositories.CharacteristicRepository;
import it.deltax.produlytics.api.repositories.DetectionRepository;
import it.deltax.produlytics.api.repositories.DeviceRepository;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import it.deltax.produlytics.persistence.DetectionEntity;
import it.deltax.produlytics.persistence.DetectionEntityId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@SuppressWarnings("unused")
public class DetectionsAdapter implements FindDeviceByApiKeyPort,
	FindCharacteristicPort,
	FindLastDetectionsPort,
	InsertDetectionPort,
	MarkOutlierPort
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
			.map(characteristicEntity -> new CharacteristicInfo(characteristicEntity.getArchived(), new LimitsInfo(
				Optional.ofNullable(characteristicEntity.getSampleSize()),
				Optional.ofNullable(characteristicEntity.getLowerLimit()),
				Optional.ofNullable(characteristicEntity.getUpperLimit()),
				Optional.ofNullable(characteristicEntity.getAverage())
			)));
	}

	@Override
	public List<Detection> findLastDetections(int deviceId, int characteristicId, int count) {
		return this.detectionRepository.findLastNById(deviceId, characteristicId, count)
			.stream()
			.map(detectionEntity -> new Detection(detectionEntity.getId().getDeviceId(),
				detectionEntity.getId().getCharacteristicId(),
				detectionEntity.getId().getCreationTime(),
				detectionEntity.getValue(),
				detectionEntity.getOutlier()
			))
			.toList();
	}

	@Override
	public void insertDetection(Detection detection) {
		DetectionEntity detectionEntity = new DetectionEntity(new DetectionEntityId(detection.creationTime(),
			detection.characteristicId(),
			detection.deviceId()
		),
			detection.value(),
			detection.outlier()
		);
		this.detectionRepository.save(detectionEntity);
	}

	@Override
	public void markOutlier(Detection detection) {
		this.detectionRepository.markOutlier(detection.deviceId(),
			detection.characteristicId(),
			detection.creationTime()
		);
	}
}
