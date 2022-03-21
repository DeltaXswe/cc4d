package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.devices.web.DevicesController;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class UiBackendApplicationTests {

	@Autowired
	private DevicesController devicesController;

	@Autowired
	private DeviceRepository repo;

	@Autowired
	private MockMvc mockMvc;


	@Test
	void contextLoads() {
		assertThat(devicesController).isNotNull();
		assertThat(repo).isNotNull();
	}

	@Test
	public void getDevices() throws Exception {
		repo.save(new DeviceEntity("One", false, false, ""));

		mockMvc.perform(get("/devices"))
				.andDo(print())
				.andExpect(status().isOk());

	}
}
