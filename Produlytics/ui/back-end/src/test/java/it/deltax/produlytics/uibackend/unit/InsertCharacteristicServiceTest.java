package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedDevicePort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.InsertCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.services.InsertCharacteristicService;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * Test di unità della classe InsertCharacteristicService
 */
public class InsertCharacteristicServiceTest {
	@Test
	void testInsertCharacteristicWithNoAutoAdjust() throws BusinessException {
		InsertCharacteristicService service = new InsertCharacteristicService(
			new InsertCharacteristicPortMock(),
			new FindDetailedDevicePortMock(),
			new FindDetailedCharacteristicPortMock()
		);

		assert service.insertByDevice(
			1,
			NewCharacteristic.builder()
				.withName("pressione")
				.withLowerLimit(OptionalDouble.of(10d))
				.withUpperLimit(OptionalDouble.of(100d))
				.withAutoAdjust(false)
				.build()
		) == 1;
	}

	@Test
	void testInsertCharacteristicWithAutoAdjust() throws BusinessException {
		InsertCharacteristicService service = new InsertCharacteristicService(
			new InsertCharacteristicPortMock(),
			new FindDetailedDevicePortMock(),
			new FindDetailedCharacteristicPortMock()
		);

		assert service.insertByDevice(
			1,
			NewCharacteristic.builder()
				.withName("pressione")
				.withAutoAdjust(true)
				.withSampleSize(OptionalInt.of(10))
				.build()
		) == 1;
	}

	@Test
	void testInsertCharacteristicWithAutoAdjustAndLimits() throws BusinessException {
		InsertCharacteristicService service = new InsertCharacteristicService(
			new InsertCharacteristicPortMock(),
			new FindDetailedDevicePortMock(),
			new FindDetailedCharacteristicPortMock()
		);

		assert service.insertByDevice(
			1,
			NewCharacteristic.builder()
				.withName("pressione")
				.withLowerLimit(OptionalDouble.of(10d))
				.withUpperLimit(OptionalDouble.of(100d))
				.withAutoAdjust(true)
				.withSampleSize(OptionalInt.of(10))
				.build()
		) == 1;
	}

	@Test
	void testInsertCharacteristicWithNoAutoAdjustAndNoLimits() {
		InsertCharacteristicService service = new InsertCharacteristicService(
			new InsertCharacteristicPortMock(),
			new FindDetailedDevicePortMock(),
			new FindDetailedCharacteristicPortMock()
		);

		BusinessException exception = assertThrows(BusinessException.class, () -> service.insertByDevice(
			1,
			NewCharacteristic.builder()
				.withName("pressione")
				.withAutoAdjust(false)
				.build()
		));
		assert exception.getCode().equals("invalidValues");
		assert exception.getType() == ErrorType.GENERIC;
	}

	@Test
	void testInsertCharacteristicWithAutoAdjustAndNoSampleSize() {
		InsertCharacteristicService service = new InsertCharacteristicService(
			new InsertCharacteristicPortMock(),
			new FindDetailedDevicePortMock(),
			new FindDetailedCharacteristicPortMock()
		);

		BusinessException exception = assertThrows(BusinessException.class, () -> service.insertByDevice(
			1,
			NewCharacteristic.builder()
				.withName("pressione")
				.withAutoAdjust(true)
				.build()
		));
		assert exception.getCode().equals("invalidValues");
		assert exception.getType() == ErrorType.GENERIC;
	}

	@Test
	void testInsertDuplicateCharacteristic() {
		InsertCharacteristicService service = new InsertCharacteristicService(
			new InsertCharacteristicPortMock(),
			new FindDetailedDevicePortMock(),
			new FindDetailedCharacteristicDuplicatePortMock()
		);

		BusinessException exception = assertThrows(BusinessException.class, () -> service.insertByDevice(
			1,
			NewCharacteristic.builder()
				.withName("pressione")
				.withAutoAdjust(true)
				.withSampleSize(OptionalInt.of(10))
				.build()
		));
		assert exception.getCode().equals("duplicateCharacteristicName");
		assert exception.getType() == ErrorType.GENERIC;
	}

	@Test
	void testInsertCharacteristicInDeviceNotFound() {
		InsertCharacteristicService service = new InsertCharacteristicService(
			new InsertCharacteristicPortMock(),
			new FindDetailedDeviceNotFoundPortMock(),
			new FindDetailedCharacteristicPortMock()
		);

		BusinessException exception = assertThrows(BusinessException.class, () -> service.insertByDevice(
			1,
			NewCharacteristic.builder()
				.withName("pressione")
				.withAutoAdjust(true)
				.withSampleSize(OptionalInt.of(10))
				.build()
		));
		assert exception.getCode().equals("deviceNotFound");
		assert exception.getType() == ErrorType.NOT_FOUND;
	}

	// Classi mock
	private static class InsertCharacteristicPortMock implements InsertCharacteristicPort {
		@Override
		public int insertByDevice(int deviceId, NewCharacteristic characteristic) {
			return 1;
		}
	}

	private static class FindDetailedDevicePortMock implements FindDetailedDevicePort {
		@Override
		public Optional<DetailedDevice> findDetailedDevice(int deviceId) {
			return Optional.of(new DetailedDevice(
				1,
				"macchina",
				false,
				false,
				"key"
			));
		}
	}

	private static class FindDetailedDeviceNotFoundPortMock implements FindDetailedDevicePort {
		@Override
		public Optional<DetailedDevice> findDetailedDevice(int deviceId) {
			return Optional.empty();
		}
	}

	private static class FindDetailedCharacteristicPortMock implements FindDetailedCharacteristicPort {
		@Override
		public Optional<DetailedCharacteristic> findByCharacteristic(int deviceId, int characteristicId) {
			return null;
		}

		@Override
		public List<DetailedCharacteristic> findByDeviceAndName(int deviceId, String name) {
			return Collections.emptyList();
		}
	}

	private static class FindDetailedCharacteristicDuplicatePortMock implements FindDetailedCharacteristicPort {
		@Override
		public Optional<DetailedCharacteristic> findByCharacteristic(int deviceId, int characteristicId) {
			return null;
		}

		@Override
		public List<DetailedCharacteristic> findByDeviceAndName(int deviceId, String name) {
			return List.of(
				DetailedCharacteristic.builder()
					.withId(1)
					.withDeviceId(1)
					.withName("pressione")
					.withAutoAdjust(true)
					.withSampleSize(OptionalInt.of(0))
					.build()
			);
		}
	}
}
