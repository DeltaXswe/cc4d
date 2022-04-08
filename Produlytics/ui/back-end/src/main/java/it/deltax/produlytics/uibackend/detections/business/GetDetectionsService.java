package it.deltax.produlytics.uibackend.detections.business;

import it.deltax.produlytics.uibackend.detections.business.domain.Detection;
import it.deltax.produlytics.uibackend.detections.business.domain.DetectionFilters;
import it.deltax.produlytics.uibackend.detections.business.domain.Detections;
import it.deltax.produlytics.uibackend.detections.business.ports.in.GetDetectionsUseCase;
import it.deltax.produlytics.uibackend.detections.business.ports.out.FindAllDetectionsPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicLimitsPort;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import it.deltax.produlytics.uibackend.exceptions.exceptions.BusinessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalLong;

@Service
public class GetDetectionsService implements GetDetectionsUseCase {
	private final FindAllDetectionsPort port;
	private final FindCharacteristicLimitsPort findCharacteristicPort;

	public GetDetectionsService(
		FindAllDetectionsPort port,
		FindCharacteristicLimitsPort findCharacteristicPort
	) {
		this.port = port;
		this.findCharacteristicPort = findCharacteristicPort;
	}

	@Override
	public Detections listByCharacteristic(int deviceId, int characteristicId, DetectionFilters filters)
	throws BusinessException {
		if (findCharacteristicPort.findByCharacteristic(deviceId, characteristicId).isEmpty()) {
			throw new BusinessException("characteristicNotFound", ErrorType.NOT_FOUND);
		}

		List<Detection> detections = port.findAllByCharacteristic(deviceId, characteristicId, filters.olderThan());
		final int initialSize = detections.size();

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
