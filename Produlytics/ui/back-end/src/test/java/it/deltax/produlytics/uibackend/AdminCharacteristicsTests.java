package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.repositories.CharacteristicRepository;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import net.minidev.json.JSONObject;
import static org.assertj.core.api.Assertions.assertThat;
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
	private CharacteristicRepository characteristicRepository;

	@BeforeAll
	private static void prepareContext(@Autowired DeviceRepository deviceRepository) {
		deviceRepository.saveAndFlush(new DeviceEntity(
			"macchina",
			false,
			false,
			"a"
		));
	}

	@BeforeEach
	private void cleanCharacteristics() {
		characteristicRepository.deleteAll();
	}

	@Test
	void contextLoads() {
		assertThat(this.characteristicRepository).isNotNull();
	}

	@Test
	void insertCharacteristic() throws Exception {
		JSONObject body = new JSONObject();
		body.put("name", "pressione");
		body.put("autoAdjust", "true");
		body.put("archived", "false");

		this.mockMvc.perform(post("/admins/devices/1/characteristics")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body.toString())
				.characterEncoding("utf-8")
			)
			.andDo(print())
			.andExpect(status().isOk());
	}
}
