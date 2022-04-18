package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.detections.business.domain.Detection;
import it.deltax.produlytics.uibackend.detections.business.domain.DetectionFilters;
import it.deltax.produlytics.uibackend.detections.business.domain.DetectionsGroup;
import it.deltax.produlytics.uibackend.detections.business.ports.out.FindAllDetectionsPort;
import it.deltax.produlytics.uibackend.detections.business.services.GetDetectionsService;
import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLimits;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicLimitsPort;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.OptionalLong;

/**
 * Test di unità della classe GetDetectionsService
 */
public class GetDetectionsServiceTest {
	private static GetDetectionsService service;

	@Disabled
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

		assert detections.detections().equals(List.of(
			new Detection(
				1,
				100,
				false
			),
			new Detection(
				2,
				200,
				false
			),
			new Detection(
				3,
				300,
				false
			)
		));
		assert detections.nextOld().isEmpty();
		assert detections.nextNew() == 3;
	}

	// Classi Mock
	private static class FindAllDetectionsPortMock implements FindAllDetectionsPort {
		@Override
		public List<Detection> findAllByCharacteristic(int deviceId, int characteristicId, OptionalLong olderThan) {
			long base = olderThan.isPresent() ? olderThan.getAsLong() : 0;
			return List.of(
				new Detection(
					base + 1,
					100 * (base + 1),
					false
				),
				new Detection(
					base + 2,
					100 * (base + 2),
					false
				),
				new Detection(
					base + 3,
					100 * (base + 3),
					false
				)
			);
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
