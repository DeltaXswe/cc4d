package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.admins.devices.business.services.UpdateDeviceNameService;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedDevicePort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.UpdateDeviceNamePort;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * Test di unità della classe UpdateDeviceNameService
 */
public class UpdateDeviceNameServiceTest {
	/**
	 * Testa il caso in cui la macchina non sia stata trovata
	 * @throws BusinessException la macchina non è stata trovata
	 */
	@Test
	void testDeviceNotFound() throws BusinessException {
		TinyDevice device = new TinyDevice(1, "macchinaNuovoNome");
		UpdateDeviceNameService service = new UpdateDeviceNameService(
			new UpdateDeviceNameServiceTest.FindDetailedDeviceNotFoundPortMock(),
			new UpdateDeviceNameServiceTest.UpdateDeviceNamePortMock()
			);

		BusinessException exception = assertThrows(BusinessException.class,
			() -> service.updateDeviceName(device));
		assert exception.getMessage().equals("deviceNotFound");
		assert exception.getType() == ErrorType.NOT_FOUND;
	}

	/**
	 * Testa il caso in cui il nome viene aggiornato correttamente
	 * @throws BusinessException la macchina non è stata trovata
	 */
	@Test
	void testOk() throws BusinessException {
		TinyDevice device = new TinyDevice(1, "macchinaNuovoNome");
		UpdateDeviceNameService service = new UpdateDeviceNameService(
			new UpdateDeviceNameServiceTest.FindDetailedDevicePortMock(),
			new UpdateDeviceNameServiceTest.UpdateDeviceNamePortMock()
		);

		service.updateDeviceName(device);
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

	static class UpdateDeviceNamePortMock implements UpdateDeviceNamePort{
		@Override
		public void updateDeviceName(DetailedDevice device) {}
	}
}