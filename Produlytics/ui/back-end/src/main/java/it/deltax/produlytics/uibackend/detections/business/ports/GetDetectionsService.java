package it.deltax.produlytics.uibackend.detections.business.ports;

import it.deltax.produlytics.uibackend.detections.business.domain.Detection;
import it.deltax.produlytics.uibackend.detections.business.domain.DetectionFilters;
import it.deltax.produlytics.uibackend.detections.business.domain.Detections;
import it.deltax.produlytics.uibackend.detections.business.ports.in.GetDetectionsUseCase;
import it.deltax.produlytics.uibackend.detections.business.ports.out.FindAllDetectionsPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.*;

@Service
public class GetDetectionsService implements GetDetectionsUseCase {
	FindAllDetectionsPort port;

	public GetDetectionsService(FindAllDetectionsPort port) {
		this.port = port;
	}

	@Override
	public Detections listByCharacteristic(int deviceId, int characteristicId, DetectionFilters filters)
	throws BusinessException {
		List<Detection> detections = port.findAllByCharacteristic(deviceId, characteristicId, new DetectionFilters(
			filters.olderThan(),
			filters.newerThan(),
			// Prendo una rilevazione in più per sapere se ce ne sono altre
			filters.limit().isPresent() ? OptionalInt.of(filters.limit().getAsInt() + 1) : OptionalInt.empty()
		));

		if (detections.isEmpty())    // Attenzione: se non ci sono rilevazioni?
			throw new BusinessException("characteristicNotFound", ErrorType.NOT_FOUND);

		Optional<Instant> nextOld;
		Instant nextNew = detections.get(detections.size() - 1).creationTime();

		// :)
		if (filters.newerThan().isPresent()) {
			List<Detection> filteredDetections = detections.stream()
				.filter(detection -> detection.creationTime().toEpochMilli() > filters.newerThan().get().toEpochMilli())
				.toList();

			if (filters.limit().isEmpty())
				nextOld = filteredDetections.size() < detections.size() ?
						  Optional.of(filteredDetections.get(0).creationTime()) :
						  Optional.empty();
			else {
				if (filteredDetections.size() > filters.limit().getAsInt()) {
					filteredDetections.remove(0);
					nextOld = Optional.of(filteredDetections.get(0).creationTime());
				}
				else
					nextOld = Optional.empty();
			}

			detections = filteredDetections;
		}
		else {
			if (filters.limit().isPresent() && detections.size() > filters.limit().getAsInt()) {
				detections.remove(0);
				nextOld = Optional.of(detections.get(0).creationTime());
			}
			else
				nextOld = Optional.empty();
		}

		return new Detections(detections, nextOld, nextNew);
	}
}
