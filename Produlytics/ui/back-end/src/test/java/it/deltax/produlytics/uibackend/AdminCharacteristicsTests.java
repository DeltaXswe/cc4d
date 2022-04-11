package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.repositories.CharacteristicRepository;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import net.minidev.json.JSONObject;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test d'integrazione per le operazioni svolte dagli amministratori relative alle caratteristiche
 * @author Alberto Lazati
 */
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class AdminCharacteristicsTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private CharacteristicRepository characteristicRepository;

	private static String url;

	private final JSONObject body = new JSONObject();

	@BeforeAll
	private static void prepareContext(@Autowired DeviceRepository deviceRepository) {
		DeviceEntity device = deviceRepository.save(new DeviceEntity(
			"macchina",
			false,
			false,
			"a"
		));

		url = "/admins/devices/" + device.getId() + "/characteristics";
	}

	@AfterAll
	private static void deleteAll(
		@Autowired DeviceRepository deviceRepository,
		@Autowired CharacteristicRepository characteristicRepository
	) {
		characteristicRepository.deleteAll();
		deviceRepository.deleteAll();
	}

	@BeforeEach
	private void cleanCharacteristics() {
		this.characteristicRepository.deleteAll();

		this.body.put("name", "pressione");
		this.body.put("autoAdjust", "true");
		this.body.put("archived", "false");
	}

	@Test
	void contextLoads() {
		assertThat(this.deviceRepository).isNotNull();
		assertThat(this.characteristicRepository).isNotNull();
	}
/*
	@Test
	void insertCharacteristic() throws Exception {
		this.mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.body.toString())
				.characterEncoding("utf-8")
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	void duplicateError() throws Exception {
		JSONObject response = new JSONObject();
		response.put("errorCode", "duplicateCharacteristicName");

		this.mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.body.toString())
				.characterEncoding("utf-8")
			);
		this.mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.body.toString())
				.characterEncoding("utf-8")
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().json(response.toJSONString()));
	}

	@Test
	void deviceNotFoundError() throws Exception {
		this.deviceRepository.deleteAll();

		JSONObject response = new JSONObject();
		response.put("errorCode", "deviceNotFound");

		this.mockMvc.perform(post(url)
				.contentType(MediaType.APPLICATION_JSON)
				.content(this.body.toString())
				.characterEncoding("utf-8")
			)
			.andExpect(status().isNotFound())
			.andExpect(content().json(response.toJSONString()));

		prepareContext(deviceRepository);
	}

 */
}
