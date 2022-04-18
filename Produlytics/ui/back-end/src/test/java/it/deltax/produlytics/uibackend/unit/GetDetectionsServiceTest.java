package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.detections.business.domain.Detection;
import it.deltax.produlytics.uibackend.detections.business.domain.DetectionFilters;
import it.deltax.produlytics.uibackend.detections.business.domain.DetectionsGroup;
import it.deltax.produlytics.uibackend.detections.business.ports.out.FindAllDetectionsPort;
import it.deltax.produlytics.uibackend.detections.business.services.GetDetectionsService;
import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLimits;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicLimitsPort;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * Test di unità della classe GetDetectionsService
 */
public class GetDetectionsServiceTest {
	private static GetDetectionsService service;

	private static final Detection detection1 = new Detection(
		1,
		100,
		false
	);

	private static final Detection detection2 = new Detection(
		2,
		200,
		false
	);

	private static final Detection detection3 = new Detection(
		3,
		300,
		false
	);

	@Test
	void testGetDetectionsWithNoFilters() throws BusinessException {
		service = new GetDetectionsService(
			new FindAllDetectionsPortMock(),
			new FindCharacteristicLimitsPortMock()
		);

		DetectionsGroup detections = service.listByCharacteristic(
			1,
			1,
			DetectionFilters.builder()
				.build()
		);

		assert detections.detections().equals(List.of(detection1, detection2, detection3));
		assert detections.nextOld().isEmpty();
		assert detections.nextNew() == 3;
	}

	@Test
	void testGetDetectionsWithLimit() throws BusinessException {
		service = new GetDetectionsService(
			new FindAllDetectionsPortMock(),
			new FindCharacteristicLimitsPortMock()
		);

		DetectionsGroup detections = service.listByCharacteristic(
			1,
			1,
			DetectionFilters.builder()
				.withLimit(OptionalInt.of(1))
				.build()
		);

		assert detections.detections().equals(List.of(detection3));
		assert detections.nextOld().getAsLong() == 3;
		assert detections.nextNew() == 3;
	}

	// Classi Mock
	private static class FindAllDetectionsPortMock implements FindAllDetectionsPort {
		@Override
		public List<Detection> findAllByCharacteristic(int deviceId, int characteristicId, OptionalLong olderThan) {
			return List.of(detection3, detection2, detection1);
		}
	}

	private static class FindAllDetectionsEmptyPortMock implements FindAllDetectionsPort {
		@Override
		public List<Detection> findAllByCharacteristic(int deviceId, int characteristicId, OptionalLong olderThan) {
			return Collections.emptyList();
		}
	}

	private static class FindCharacteristicLimitsPortMock implements FindCharacteristicLimitsPort {
		@Override
		public Optional<CharacteristicLimits> findByCharacteristic(int deviceId, int characteristicId) {
			return Optional.of(CharacteristicLimits.newCharacteristicLimits(
				10d,
				100d
			));
		}
	}

	private static class FindCharacteristicLimitsNotFoundPortMock implements FindCharacteristicLimitsPort {
		@Override
		public Optional<CharacteristicLimits> findByCharacteristic(int deviceId, int characteristicId) {
			return Optional.empty();
		}
	}
}
