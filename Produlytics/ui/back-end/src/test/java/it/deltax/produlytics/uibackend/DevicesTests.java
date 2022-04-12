package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.devices.web.DevicesController;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
class DevicesTests {

	@Autowired
	private DevicesController devicesController;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	protected MockMvc mockMvc;

	private static int deviceId1;
	private static int deviceId2;

	@BeforeEach
	private void prepareContext(
		@Autowired DeviceRepository deviceRepository) {
		DeviceEntity device1 = deviceRepository.save(new DeviceEntity(
			"macchina1",
			false,
			false,
			"a1"
		));
		deviceId1=device1.getId();

		DeviceEntity device2 = deviceRepository.save(new DeviceEntity(
			"macchina2",
			false,
			false,
			"a2"
		));
		deviceId2=device2.getId();
	}

	@AfterEach
	private void deleteAll(
		@Autowired DeviceRepository deviceRepository) {
		deviceRepository.deleteAll();
	}

	@Test
	void contextLoads() {
		assertThat(this.devicesController).isNotNull();
		assertThat(this.deviceRepository).isNotNull();
	}

	@Test
	public void getAllUnarchivedDevices() throws Exception {
		JSONObject d1 = new JSONObject().put("id", deviceId1)
			.put("name", "macchina1");
		JSONObject d2 = new JSONObject().put("id", deviceId2)
			.put("name", "macchina2");
		String requestResponse = new JSONArray().put(d1).put(d2).toString();

		this.mockMvc.perform(get("/devices"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json(requestResponse));
	}

	@Test
	public void getAllUnarchivedDevicesWhenThereAreNone() throws Exception {
		deleteAll(deviceRepository);
		this.mockMvc.perform(get("/devices"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json("[]"));
	}
}
