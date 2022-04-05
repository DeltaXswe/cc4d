package it.deltax.produlytics.uibackend.detections.adapters;

import it.deltax.produlytics.persistence.DetectionEntity;
import it.deltax.produlytics.uibackend.detections.business.domain.Detection;
import it.deltax.produlytics.uibackend.detections.business.domain.DetectionFilters;
import it.deltax.produlytics.uibackend.detections.business.ports.out.FindAllDetectionsPort;
import it.deltax.produlytics.uibackend.repositories.DetectionRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.OptionalLong;
import java.util.stream.Collectors;

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
		List<DetectionEntity> detections;
		OptionalLong olderThan = filters.olderThan();

		detections = repo.findByCharacteristicAndCreationTimeGreaterThanAndLessThanQuery(deviceId,
			characteristicId,
			olderThan.isPresent() ? Instant.ofEpochMilli(olderThan.getAsLong()) : null,
			Sort.by(Sort.Direction.DESC)
		); return detections.stream()
			.map(
			))
			.collect(Collectors.toList());
	}
}
