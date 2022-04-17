package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.devices.business.services.GetUnarchivedDevicesService;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.devices.business.ports.out.GetUnarchivedDevicesPort;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Test di unità della classe GetUnarchivedDevicesService
 */
public class GetUnarchivedDevicesServiceTest {
	/**
	 * Testa il caso in cui ci sono dei dispositivi non archiviati
	 */
	@Test
	void testGetUnarchivedDevices(){
		GetUnarchivedDevicesService service = new GetUnarchivedDevicesService(
			new GetUnarchivedDevicesPortMock()
		);
		service.getUnarchivedDevices();
	}

	/**
	 * Testa il caso in cui non ci sono dei dispositivi non archiviati
	 */
	@Test
	void testGetNoUnarchivedDevices(){
		GetUnarchivedDevicesService service = new GetUnarchivedDevicesService(
			new GetNoUnarchivedDevicesPortMock()
		);
		service.getUnarchivedDevices();
	}


	// CLASSI MOCK
	static class GetUnarchivedDevicesPortMock implements GetUnarchivedDevicesPort{
		@Override
		public List<TinyDevice> getUnarchivedDevices() {
			TinyDevice tinyDevice1 = new TinyDevice(1, "device1");
			TinyDevice tinyDevice2 = new TinyDevice(2, "device2");
			return new ArrayList<TinyDevice>(Arrays.asList(tinyDevice1,tinyDevice2));
		}
	}

	static class GetNoUnarchivedDevicesPortMock implements GetUnarchivedDevicesPort{
		@Override
		public List<TinyDevice> getUnarchivedDevices() {
			return null;
		}
	}
}
