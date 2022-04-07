package it.deltax.produlytics.uibackend.detections.business;

import it.deltax.produlytics.uibackend.detections.business.domain.Detection;
import it.deltax.produlytics.uibackend.detections.business.domain.DetectionFilters;
import it.deltax.produlytics.uibackend.detections.business.domain.Detections;
import it.deltax.produlytics.uibackend.detections.business.ports.in.GetDetectionsUseCase;
import it.deltax.produlytics.uibackend.detections.business.ports.out.FindAllDetectionsPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GetDetectionsService implements GetDetectionsUseCase {
	private final FindAllDetectionsPort port;

	public GetDetectionsService(FindAllDetectionsPort port) {
		this.port = port;
	}

	@Override
	public Detections listByCharacteristic(int deviceId, int characteristicId, DetectionFilters filters)
	throws BusinessException {
		List<Detection> detections = port.findAllByCharacteristic(deviceId, characteristicId, filters.olderThan());
		final int initialSize = detections.size();

		if (detections.isEmpty())    // Attenzione: se non ci sono rilevazioni?
			throw new BusinessException("characteristicNotFound", ErrorType.NOT_FOUND);

		if (filters.limit().isPresent()) {
			detections = detections.stream()
				.limit(filters.limit().getAsInt())
				.toList();
		}

		if (filters.newerThan().isPresent()) {
			detections = detections.stream()
				.filter(detection -> detection.creationTime() > filters.newerThan().getAsLong())
				.toList();
		}

		final long nextNew = detections.get(0).creationTime();
		final OptionalLong nextOld = detections.size() < initialSize
				  ? OptionalLong.of(detections.get(detections.size() - 1).creationTime())
				  : OptionalLong.empty();

		List<Detection> reversedDetections = new ArrayList<>(detections);
		Collections.reverse(reversedDetections);

		return new Detections(reversedDetections, nextOld, nextNew);
	}
}
