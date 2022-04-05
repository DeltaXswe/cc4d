package it.deltax.produlytics.uibackend.detections.adapters;

import it.deltax.produlytics.uibackend.detections.business.domain.Detection;
import it.deltax.produlytics.uibackend.detections.business.domain.DetectionFilters;
import it.deltax.produlytics.uibackend.detections.business.ports.out.FindAllDetectionsPort;
import it.deltax.produlytics.uibackend.repositories.DetectionRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DetectionAdapter implements FindAllDetectionsPort {
	private final DetectionRepository repo;

	public DetectionAdapter(DetectionRepository repo) {
		this.repo = repo;
	}

	@Override
	public List<Detection> findAllByCharacteristic(
		int deviceId, int characteristicId, DetectionFilters filters
	) {
		return repo.findByCharacteristicAndCreationTimeGreaterThanQuery(deviceId,
				characteristicId,
				filters.olderThan().orElse(null),
				PageRequest.ofSize(filters.limit().orElse(Integer.MAX_VALUE))
					.withSort(Sort.Direction.ASC)
			)
			.stream()
			.map(detection -> new Detection(detection.getId().getCreationTime(),
				detection.getValue(),
				detection.getOutlier()
			))
			.toList();
	}
}
