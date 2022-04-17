package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.admins.devices.business.services.UpdateDeviceDeactivateStatusService;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceDeactivateStatus;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedDevicePort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.UpdateDeviceDeactivateStatusPort;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * Test di unità della classe UpdateDeviceDeactivateStatusService
 */
public class UpdateDeviceDeactivateStatusServiceTest {
	/**
	 * Testa il caso in cui la macchina non sia stata trovata
	 * @throws BusinessException la macchina non è stata trovata
	 */
	@Test
	void testDeviceNotFound() throws BusinessException {
		DeviceDeactivateStatus device = new DeviceDeactivateStatus(1, true);
		UpdateDeviceDeactivateStatusService service = new UpdateDeviceDeactivateStatusService(
			new UpdateDeviceDeactivateStatusServiceTest.FindDetailedDeviceNotFoundPortMock(),
			new UpdateDeviceDeactivateStatusServiceTest.UpdateDeviceDeactivateStatusPortMock());

		BusinessException exception = assertThrows(BusinessException.class,
			() -> service.updateDeviceDeactivateStatus(device));
		assert exception.getMessage().equals("deviceNotFound");
		assert exception.getType() == ErrorType.NOT_FOUND;
	}

	/**
	 * Testa il caso in cui lo stato di attivazione viene aggiornato correttamente
	 * @throws BusinessException la macchina non è stata trovata
	 */
	@Test
	void testOk() throws BusinessException {
		DeviceDeactivateStatus device = new DeviceDeactivateStatus(1, true);
		UpdateDeviceDeactivateStatusService service = new UpdateDeviceDeactivateStatusService(
			new UpdateDeviceDeactivateStatusServiceTest.FindDetailedDevicePortMock(),
			new UpdateDeviceDeactivateStatusServiceTest.UpdateDeviceDeactivateStatusPortMock());

		service.updateDeviceDeactivateStatus(device);
	}

	//CLASSI MOCK
	static class FindDetailedDevicePortMock implements FindDetailedDevicePort {
		@Override
		public Optional<DetailedDevice> findDetailedDevice(
			int deviceId
		) {
			return Optional.of(new DetailedDevice(1, "macchina1", false, false, ""));
		}
	}

	static class FindDetailedDeviceNotFoundPortMock implements FindDetailedDevicePort {
		@Override
		public Optional<DetailedDevice> findDetailedDevice(int deviceId) {
			return Optional.empty();
		}
	}

	static class UpdateDeviceDeactivateStatusPortMock implements UpdateDeviceDeactivateStatusPort {
		@Override
		public void updateDeviceDeactivateStatus(DetailedDevice device) {}
	}
}
