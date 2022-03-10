package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.uibackend.common.machine.web.MachinesController;
import it.deltax.produlytics.uibackend.repositories.MacchinaRepository;
import static org.assertj.core.api.Assertions.assertThat;

import it.deltax.produlytics.persistence.configurazione.Macchina;
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
	private MachinesController machineController;

	@Autowired
	private MacchinaRepository macchinaRepository;

	@Autowired
	private MockMvc mockMvc;


	@Test
	void contextLoads() {
		assertThat(machineController).isNotNull();
		assertThat(macchinaRepository).isNotNull();
	}

	@Test
	public void listAllMacchine() throws Exception {
		macchinaRepository.save(new Macchina("One"));

		mockMvc.perform(get("/machines"))
				.andDo(print())
				.andExpect(status().isOk());

	}
}
