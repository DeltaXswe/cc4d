package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.*;
import it.deltax.produlytics.uibackend.detections.web.DetectionsController;
import it.deltax.produlytics.uibackend.repositories.CharacteristicRepository;
import it.deltax.produlytics.uibackend.repositories.DetectionRepository;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;

public class DetectionsTests extends UiBackendApplicationTests {
	@Autowired
	private DetectionRepository repository;

	@Autowired
	private DetectionsController controller;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private CharacteristicRepository characteristicRepository;

	private final DeviceEntity device = new DeviceEntity("macchina", false, false, "x");
	private final CharacteristicEntity characteristic = new CharacteristicEntity(
		new CharacteristicEntityId(1, 1),
		"temperatura",
		98d,
		-13d,
		true,
		0,
		false
	);

	private void prepareContext() {
		deviceRepository.saveAndFlush(device);
		characteristicRepository.saveAndFlush(characteristic);
		for (int i = 1; i < 5; ++i) {
			repository.save(new DetectionEntity(
				new DetectionEntityId(Instant.ofEpochMilli(i), 1, 1),
				100d * i,
				false
			));
		}
	}

	@Override
	@Test
	void contextLoads() {
		assertThat(repository).isNotNull();
		assertThat(controller).isNotNull();
	}

	@Test
	void getWithNoFilter() throws Exception {
		prepareContext();
		mockMvc.perform(get("/devices/1/characteristics/1/detections"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("{\"detections\":[{\"creationTime\":1,\"value\":100.0,\"outlier\":false},{\"creationTime\":2,\"value\":200.0,\"outlier\":false},{\"creationTime\":3,\"value\":300.0,\"outlier\":false},{\"creationTime\":4,\"value\":400.0,\"outlier\":false}],\"nextOld\":null,\"nextNew\":4}"));
	}

	@Test
	void getWithLimit() throws Exception {
		prepareContext();
		mockMvc.perform(get("/devices/1/characteristics/1/detections?limit=2"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("{\"detections\":[{\"creationTime\":3,\"value\":300.0,\"outlier\":false},{\"creationTime\":4,\"value\":400.0,\"outlier\":false}],\"nextOld\":3,\"nextNew\":4}"));
	}
}
