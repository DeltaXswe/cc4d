package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.repositories.CharacteristicRepository;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import net.minidev.json.JSONObject;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test d'integrazione per le operazioni svolte dagli amministratori relative alle caratteristiche
 * @author Alberto Lazati
 */
public class AdminCharacteristicsTests extends UiBackendApplicationTests {
	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private CharacteristicRepository characteristicRepository;

	private final CharacteristicEntity characteristic = new CharacteristicEntity(
		new CharacteristicEntityId(1, 1),
		"temperatura",
		98d,
		-13d,
		true,
		0,
		false
	);

	private final CharacteristicEntity characteristic2 = new CharacteristicEntity(
		new CharacteristicEntityId(1, 2),
		"pressione",
		100d,
		10d,
		true,
		0,
		false
	);

	@BeforeEach
	private void prepareContext() {
		this.deviceRepository.saveAndFlush(new DeviceEntity(
			"macchina",
			false,
			false,
			"a"
		));
	}

	@Override
	@Test
	void contextLoads() {
		assertThat(this.deviceRepository).isNotNull();
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
			.andExpect(status().isOk())
			.andExpect(content().string("1"));
	}
}
