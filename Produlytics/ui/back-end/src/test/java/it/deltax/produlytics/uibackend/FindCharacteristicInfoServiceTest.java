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

public class FindCharacteristicInfoServiceTest extends UiBackendApplicationTests {

	@Autowired
	private CharacteristicsController characteristicsController;

	@Autowired
	private UnarchivedCharacteristicRepository unarchivedCharacteristicRepository;

	@Override
	@Test
	void contextLoads() {
		assertThat(characteristicsController).isNotNull();
	}

	@Test
	void getUnarchivedCharacteristics() throws Exception {
		unarchivedCharacteristicRepository.save(new CharacteristicEntity(
			new CharacteristicEntityId(1, 1),
			"temperatura",
			100d,
			10d,
			true,
			0,
			false
		));
		unarchivedCharacteristicRepository.save(new CharacteristicEntity(
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
	void getUnarchivedCharacteristicsError() throws Exception {
		unarchivedCharacteristicRepository.save(new CharacteristicEntity(
			new CharacteristicEntityId(1, 1),
			"char",
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
}
