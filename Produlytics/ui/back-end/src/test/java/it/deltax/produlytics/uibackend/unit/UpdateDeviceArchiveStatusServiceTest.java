package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.admins.devices.business.services.UpdateDeviceArchiveStatusService;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceArchiveStatus;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedDevicePort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.UpdateDeviceArchiveStatusPort;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * Test di unità della classe UpdateDeviceArchiveStatusService
 */
public class UpdateDeviceArchiveStatusServiceTest {
	/**
	 * Testa il caso in cui la macchina non sia stata trovata
	 * @throws BusinessException la macchina non è stata trovata
	 */
	@Test
	void testDeviceNotFound() throws BusinessException {
		DeviceArchiveStatus device = new DeviceArchiveStatus(1, true);
		UpdateDeviceArchiveStatusService service = new UpdateDeviceArchiveStatusService(
			new UpdateDeviceArchiveStatusServiceTest.FindDetailedDeviceNotFoundPortMock(),
			new UpdateDeviceArchiveStatusServiceTest.UpdateDeviceArchiveStatusPortMock());

		BusinessException exception = assertThrows(BusinessException.class,
			() -> service.updateDeviceArchiveStatus(device));
		assert exception.getCode().equals("deviceNotFound");
		assert exception.getType() == ErrorType.NOT_FOUND;
	}

	/**
	 * Testa il caso in cui lo stato di archiviazione viene aggiornato correttamente
	 * @throws BusinessException la macchina non è stata trovata
	 */
	@Test
	void testOk() throws BusinessException {
		DeviceArchiveStatus device = new DeviceArchiveStatus(1, true);
		UpdateDeviceArchiveStatusService service = new UpdateDeviceArchiveStatusService(
			new UpdateDeviceArchiveStatusServiceTest.FindDetailedDevicePortMock(),
			new UpdateDeviceArchiveStatusServiceTest.UpdateDeviceArchiveStatusPortMock());

		service.updateDeviceArchiveStatus(device);
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

	static class UpdateDeviceArchiveStatusPortMock implements UpdateDeviceArchiveStatusPort {
		@Override
		public void updateDeviceArchiveStatus(DetailedDevice device) {}
	}
}
