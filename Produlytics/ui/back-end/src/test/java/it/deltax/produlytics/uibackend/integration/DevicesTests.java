package it.deltax.produlytics.uibackend.integration;

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

/**
 * Test d'integrazione per le operazioni relative alle macchine
 */
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

	/**
	 * Inserisce nel repository due macchine
	 * @param deviceRepository il repository in cui inserire le due macchine
	 */
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

	/**
	 * Svuota il repository
	 * @param deviceRepository il repository da svuotare
	 */
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

	/**
	 * Testa l'ottenimento di tutte le macchine non archiviate
	 * @throws Exception
	 */
	@Test
	public void testGetAllUnarchivedDevices() throws Exception {
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

	/**
	 * Testa l'ottenimento di tutte le macchine non archiviate, quando non ce ne sono
	 * @throws Exception
	 */
	@Test
	public void testGetAllUnarchivedDevicesWhenThereAreNone() throws Exception {
		deleteAll(deviceRepository);
		this.mockMvc.perform(get("/devices"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json("[]"));
	}
}
