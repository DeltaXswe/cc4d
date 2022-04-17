package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.admins.devices.business.GetDevicesService;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.Device;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.GetDevicesPort;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * Test di unità della classe GetDevicesService
 */
public class GetDevicesServiceTest {
	/**
	 * Testa l'ottenimento della lista delle macchine
	 */
	@Test
	void testGetDevices() {
		GetDevicesService service = new GetDevicesService(new GetDevicesServiceTest.GetDevicesPortMock());

		service.getDevices();
	}

	//CLASSI MOCK
	public static class GetDevicesPortMock implements GetDevicesPort{
		@Override
		public List<Device> getDevices() {
			return null;
		}
	}
}
