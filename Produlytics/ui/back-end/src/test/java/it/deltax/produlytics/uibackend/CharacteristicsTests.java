package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import it.deltax.produlytics.uibackend.devices.web.CharacteristicsController;
import it.deltax.produlytics.uibackend.repositories.UnarchivedCharacteristicRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CharacteristicsTests extends UiBackendApplicationTests {
	@Autowired
	private UnarchivedCharacteristicRepository repository;

	@Autowired
	private CharacteristicsController controller;

	@Override
	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

	@Test
	void getUnarchivedCharacteristics() throws Exception {
		repository.save(new CharacteristicEntity(
			new CharacteristicEntityId(1, 1),
			"temperatura",
			100d,
			10d,
			true,
			0,
			false
		));
		repository.save(new CharacteristicEntity(
			new CharacteristicEntityId(1, 2),
			"pressione",
			100d,
			10d,
			true,
			0,
			false
		));

		mockMvc.perform(get("/devices/1/characteristics"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content()
				.string("[{\"id\":1,\"name\":\"temperatura\"},{\"id\":2,\"name\":\"pressione\"}]")
			);
	}

	@Test
	void deviceNotFoundError() throws Exception {
		repository.save(new CharacteristicEntity(
			new CharacteristicEntityId(1, 1),
			"temperatura",
			100d,
			10d,
			true,
			0,
			false
		));

		mockMvc.perform(get("/devices/2/characteristics"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("{\"errorCode\":\"deviceNotFound\"}"));
	}

	@Test
	void getCharacteristicLimits() throws Exception {
		repository.save(new CharacteristicEntity(
			new CharacteristicEntityId(1, 1),
			"temperatura",
			98d,
			-13d,
			true,
			0,
			false
		));

		mockMvc.perform(get("/devices/1/characteristics/1/limits"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("{\"lowerLimit\":-13.0,\"upperLimit\":98.0,\"mean\":42.5}"));
	}

	@Test
	void characteristicNotFoundError1() throws Exception {
		repository.save(new CharacteristicEntity(
			new CharacteristicEntityId(1, 1),
			"temperatura",
			98d,
			-13d,
			true,
			0,
			false
		));

		mockMvc.perform(get("/devices/1/characteristics/2/limits"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("{\"errorCode\":\"characteristicNotFound\"}"));
	}

	@Test
	void characteristicNotFoundError2() throws Exception {
		repository.save(new CharacteristicEntity(
			new CharacteristicEntityId(1, 1),
			"temperatura",
			98d,
			-13d,
			true,
			0,
			false
		));

		mockMvc.perform(get("/devices/2/characteristics/2/limits"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("{\"errorCode\":\"characteristicNotFound\"}"));
	}
}
