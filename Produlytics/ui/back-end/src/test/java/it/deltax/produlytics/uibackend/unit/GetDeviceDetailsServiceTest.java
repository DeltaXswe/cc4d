package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.admins.devices.business.services.GetDeviceDetailsService;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedDevicePort;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

import java.util.Optional;

/**
 * Test di unità della classe GetDeviceDetailsService
 */
public class GetDeviceDetailsServiceTest {

	@Test
	void testDeviceNotFound() throws BusinessException {
		GetDeviceDetailsService service = new GetDeviceDetailsService(
			new GetDeviceDetailsServiceTest.FindDetailedDeviceNotFoundPortMock());

		BusinessException exception = assertThrows(BusinessException.class,
			() -> service.getDeviceDetails(1));
		assert exception.getMessage().equals("deviceNotFound");
		assert exception.getType() == ErrorType.NOT_FOUND;
	}

	/**
	 * Testa il caso in cui l'ottenimento dei dettagli avviene correttamente
	 * @throws BusinessException la macchina non è stata trovata
	 */
	@Test
	void testOk() throws BusinessException {
		GetDeviceDetailsService service = new GetDeviceDetailsService(
			new GetDeviceDetailsServiceTest.FindDetailedDevicePortMock());

		service.getDeviceDetails(1);
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
}
