package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLimits;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicLimitsPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.GetUnarchivedDevicesPort;
import it.deltax.produlytics.uibackend.devices.business.services.GetLimitsService;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Test di unità della classe GetLimitsService
 */
public class GetLimitsServiceTest {
	private static GetLimitsService service;

	private static final TinyDevice device = new TinyDevice(
		1,
		"macchina"
	);

	@Test
	void testGetLimits() throws BusinessException {
		service = new GetLimitsService(new GetUnarchivedDevicesPortMock(), new FindCharacteristicLimitsPortMock());

		CharacteristicLimits limits = service.getByCharacteristic(1, 1);
		assert limits.lowerLimit() == 10d;
		assert limits.upperLimit() == 100d;
		assert limits.mean() == 55d;
	}

	@Test
	void testNotFoundAndArchived() {
		service = new GetLimitsService(
			new GetUnarchivedDevicesPortMock(),
			new FindCharacteristicLimitsNotFoundPortMock()
		);

		BusinessException exception = assertThrows(
			BusinessException.class,
			() -> service.getByCharacteristic(1, 1)
		);
		assert exception.getMessage().equals("characteristicNotFound");
		assert exception.getType() == ErrorType.NOT_FOUND;
	}

	@Test
	void testDeviceNotFound() {
		service = new GetLimitsService(
			new GetUnarchivedDevicesNotFoundPortMock(),
			new FindCharacteristicLimitsPortMock()
		);

		BusinessException exception = assertThrows(
			BusinessException.class,
			() -> service.getByCharacteristic(1, 1)
		);
		assert exception.getMessage().equals("characteristicNotFound");
		assert exception.getType() == ErrorType.NOT_FOUND;
	}

	// Classi mock
	private static class GetUnarchivedDevicesPortMock implements GetUnarchivedDevicesPort {
		public List<TinyDevice> getUnarchivedDevices() {
			return List.of(device);
		}
	}

	private static class GetUnarchivedDevicesNotFoundPortMock implements GetUnarchivedDevicesPort {
		public List<TinyDevice> getUnarchivedDevices() {
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
