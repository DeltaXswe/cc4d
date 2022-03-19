package it.deltax.produlytics.uibackend.device;

import it.deltax.produlytics.uibackend.devices.business.FindCharacteristicInfoService;
import it.deltax.produlytics.uibackend.devices.business.domain.Characteristic;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicPort;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
public class FindCharacteristicInfoServiceTest {
	private static class FindCharacteristicMock implements FindCharacteristicPort{
		@Override
		public Optional<Characteristic> find(
			int deviceId, int id
		) {
			return Optional.of(new Characteristic(deviceId, id));
		}
	}

	@Test
	public find(){
		FindCharacteristicInfoService service = new FindCharacteristicInfoService();

	}
}
