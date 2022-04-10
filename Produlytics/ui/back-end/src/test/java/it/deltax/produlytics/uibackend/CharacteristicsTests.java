package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.uibackend.devices.web.CharacteristicsController;
import it.deltax.produlytics.uibackend.repositories.CharacteristicRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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
 * Test d'integrazione per le operazioni relative alle caratteristiche
 * @author Alberto Lazari
 */
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class CharacteristicsTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CharacteristicsController controller;

	@BeforeAll
	private static void prepareContext(@Autowired CharacteristicRepository repository) {
		repository.save(new CharacteristicEntity(
			1,
			"temperatura",
			98d,
			-13d,
			true,
			0,
			false
		));

		repository.save(new CharacteristicEntity(
			1,
			"pressione",
			100d,
			10d,
			true,
			0,
			false
		));
	}

	@Test
	void contextLoads() {
		assertThat(this.controller).isNotNull();
	}

	@Test
	void getUnarchivedCharacteristics() throws Exception {
		var characteristic1 = new JSONObject();
		characteristic1.put("id", 1);
		characteristic1.put("name", "temperatura");

		var characteristic2 = new JSONObject();
		characteristic1.put("id", 2);
		characteristic1.put("name", "pressione");

		var response = new JSONArray();
		response.add(characteristic1);
		response.add(characteristic2);

		this.mockMvc.perform(get("/devices/1/characteristics"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json(response.toJSONString()));
	}

	@Test
	void deviceNotFoundError() throws Exception {
		this.mockMvc.perform(get("/devices/2/characteristics"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("{\"errorCode\":\"deviceNotFound\"}"));
	}

	@Test
	void getCharacteristicLimits() throws Exception {
		this.mockMvc.perform(get("/devices/1/characteristics/1/limits"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("{\"lowerLimit\":-13.0,\"upperLimit\":98.0,\"mean\":42.5}"));
	}

	@Test
	void characteristicNotFoundError1() throws Exception {
		this.mockMvc.perform(get("/devices/1/characteristics/3/limits"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("{\"errorCode\":\"characteristicNotFound\"}"));
	}

	@Test
	void characteristicNotFoundError2() throws Exception {
		this.mockMvc.perform(get("/devices/2/characteristics/2/limits"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("{\"errorCode\":\"characteristicNotFound\"}"));
	}
}
