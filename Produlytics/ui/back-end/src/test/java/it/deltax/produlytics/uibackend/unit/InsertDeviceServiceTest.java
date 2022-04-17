package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.admins.devices.business.CreateDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.services.InsertDeviceService;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceToInsert;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.InsertCharacteristicUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.InsertDevicePort;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * Test di unità della classe InsertDeviceService
 */
public class InsertDeviceServiceTest {
	/**
	 * Testa l'inserimento di una nuova macchina
	 * @throws BusinessException
	 */
	@Test
	void testOk() throws BusinessException {
		List<NewCharacteristic> characteristics = new ArrayList<>();
		NewCharacteristic characteristic = new NewCharacteristic(
			"caratteristica1", OptionalDouble.of(10), OptionalDouble.of(-10), true, OptionalInt.of(5));
		characteristics.add(characteristic);
		DeviceToInsert device = new DeviceToInsert("macchina1", characteristics);
		InsertDeviceService service = new InsertDeviceService(
			new InsertDeviceServiceTest.InsertDevicePortMock(),
			new InsertDeviceServiceTest.CreateDeviceMock(),
			new InsertDeviceServiceTest.InsertCharacteristicUseCaseMock()
		);

		service.insertDevice(device);
	}

	// CLASSI MOCK
	static class InsertDevicePortMock implements InsertDevicePort {
		@Override
		public int insertDevice(NewDevice device) {
			return 0;
		}
	}

	static class CreateDeviceMock extends CreateDevice {
		@Override
		public NewDevice createDevice(String name) {
			return new NewDevice(name, false, false, this.generateApiKey());
		}
	}

	static class InsertCharacteristicUseCaseMock implements InsertCharacteristicUseCase {

		@Override
		public int insertByDevice(
			int deviceId, NewCharacteristic characteristic
		) throws BusinessException {
			return 0;
		}
	}
}

