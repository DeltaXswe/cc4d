package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.CharacteristicArchiveStatus;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.UpdateCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.services.UpdateCharacteristicArchiveStatusService;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * Test di unità della classe UpdateCharacteristicArchiveStatusService
 */
public class UpdateCharacteristicArchiveStatusServiceTest {
	@Test
	void testArchiveCharacteristic() throws BusinessException {
		UpdateCharacteristicArchiveStatusService service = new UpdateCharacteristicArchiveStatusService(
			new FindDetailedCharacteristicPortMock(),
			new UpdateCharacteristicPortMock()
		);

		service.updateCharacteristicArchiveStatus(new CharacteristicArchiveStatus(
			1,
			1,
			true
		));
	}

	@Test
	void testUnarchiveCharacteristic() throws BusinessException {
		UpdateCharacteristicArchiveStatusService service = new UpdateCharacteristicArchiveStatusService(
			new FindArchivedDetailedCharacteristicPortMock(),
			new UpdateCharacteristicPortMock()
		);

		service.updateCharacteristicArchiveStatus(new CharacteristicArchiveStatus(
			1,
			1,
			false
		));
	}

	@Test
	void testArchiveCharacteristicNotFound() {
		UpdateCharacteristicArchiveStatusService service = new UpdateCharacteristicArchiveStatusService(
			new FindDetailedCharacteristicNotFoundPortMock(),
			new UpdateCharacteristicPortMock()
		);

		BusinessException exception = assertThrows(
			BusinessException.class,
			() -> service.updateCharacteristicArchiveStatus(new CharacteristicArchiveStatus(
				1,
			1,
			false
			))
		);
		assert exception.getMessage().equals("characteristicNotFound");
		assert exception.getType() == ErrorType.NOT_FOUND;
	}

	// Classi mock
	private static class FindDetailedCharacteristicPortMock implements FindDetailedCharacteristicPort {
		@Override
		public Optional<DetailedCharacteristic> findByCharacteristic(int deviceId, int characteristicId) {
			return Optional.of(DetailedCharacteristic.builder()
				.withId(1)
				.withDeviceId(1)
				.withName("temperatura")
				.withLowerLimit(OptionalDouble.of(10d))
				.withUpperLimit(OptionalDouble.of(100d))
				.withAutoAdjust(true)
				.withSampleSize(OptionalInt.of(0))
				.build()
			);
		}

		@Override
		public List<DetailedCharacteristic> findByDeviceAndName(int deviceId, String name) {
			return null;
		}
	}

	private static class FindArchivedDetailedCharacteristicPortMock implements FindDetailedCharacteristicPort {
		@Override
		public Optional<DetailedCharacteristic> findByCharacteristic(int deviceId, int characteristicId) {
			return Optional.of(DetailedCharacteristic.builder()
				.withId(1)
				.withDeviceId(1)
				.withName("temperatura")
				.withLowerLimit(OptionalDouble.of(10d))
				.withUpperLimit(OptionalDouble.of(100d))
				.withAutoAdjust(true)
				.withSampleSize(OptionalInt.of(0))
				.withArchived(true)
				.build()
			);
		}

		@Override
		public List<DetailedCharacteristic> findByDeviceAndName(int deviceId, String name) {
			return null;
		}
	}

	private static class FindDetailedCharacteristicNotFoundPortMock implements FindDetailedCharacteristicPort {
		@Override
		public Optional<DetailedCharacteristic> findByCharacteristic(int deviceId, int characteristicId) {
			return Optional.empty();
		}

		@Override
		public List<DetailedCharacteristic> findByDeviceAndName(int deviceId, String name) {
			return null;
		}
	}

	private static class UpdateCharacteristicPortMock implements UpdateCharacteristicPort {
		@Override
		public void updateCharacteristic(DetailedCharacteristic characteristic) {}
	}
}
