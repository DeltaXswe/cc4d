package it.deltax.produlytics.uibackend.detections.adapters;

import it.deltax.produlytics.uibackend.detections.business.domain.Detection;
import it.deltax.produlytics.uibackend.detections.business.ports.out.FindAllDetectionsPort;
import it.deltax.produlytics.uibackend.repositories.DetectionRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.OptionalLong;

/**
 * L'adapter dello strato di persistenza per le operazioni riguardanti le rilevazioni
 * @author Alberto Lazari
 */
@Component
public class DetectionAdapter implements FindAllDetectionsPort {
	private final DetectionRepository repo;


	/**
	 * Il costruttore
	 * @param repo lo strato di persistenza con i dati sulle rilevazioni
	 */
	public DetectionAdapter(DetectionRepository repo) {
		this.repo = repo;
	}


	@Override
	public List<Detection> findAllByCharacteristic(
		int deviceId, int characteristicId, OptionalLong olderThan
	) {
		return repo.findByCharacteristicAndCreationTimeGreaterThanQuery(
			deviceId,
			characteristicId,
			olderThan.isPresent() ? Instant.ofEpochMilli(olderThan.getAsLong()) : null,
			Sort.by(Sort.Direction.DESC, "id.creationTime")
		)
		.stream()
		.map(detection -> new Detection(
			detection.getCreationTime().toEpochMilli(),
			detection.getValue(),
			detection.getOutlier()
		))
		.toList();
	}
}
