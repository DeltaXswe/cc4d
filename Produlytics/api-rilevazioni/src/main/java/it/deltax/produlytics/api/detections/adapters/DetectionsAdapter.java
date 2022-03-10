package it.deltax.produlytics.api.detections.adapters;

import it.deltax.produlytics.api.detections.business.domain.Detection;
import it.deltax.produlytics.api.detections.business.domain.validate.ValidationInfo;
import it.deltax.produlytics.api.detections.business.ports.out.FindLastDetectionsPort;
import it.deltax.produlytics.api.detections.business.ports.out.FindValidationInfoPort;
import it.deltax.produlytics.api.detections.business.ports.out.InsertDetectionPort;
import it.deltax.produlytics.api.detections.business.ports.out.MarkOutlierPort;
import it.deltax.produlytics.api.repositories.CharacteristicRepository;
import it.deltax.produlytics.api.repositories.DetectionRepository;
import it.deltax.produlytics.persistence.DetectionEntity;
import it.deltax.produlytics.persistence.DetectionEntityId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class DetectionsAdapter
	implements FindValidationInfoPort, FindLastDetectionsPort, InsertDetectionPort, MarkOutlierPort
{
	@Autowired
	private CharacteristicRepository characteristicRepository;
	@Autowired
	private DetectionRepository detectionRepository;

	@Override
	public Optional<ValidationInfo> findValidationInfo(String apiKey, int characteristicId) {
		return this.characteristicRepository.findValidationInfo(apiKey, characteristicId)
			.map(validationInfoProjection -> new ValidationInfo(validationInfoProjection.apiKey(),
				validationInfoProjection.deviceArchived(),
				validationInfoProjection.deviceDeactivated(),
				validationInfoProjection.characteristicArchived()
			));
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
