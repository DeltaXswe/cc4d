package it.deltax.produlytics.uibackend;

import static org.assertj.core.api.Assertions.assertThat;

import it.deltax.produlytics.uibackend.web.MachinesController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@AutoConfigureMockMvc
class UiBackendApplicationTests {

	@Autowired
	private MachinesController machineController;

	// @Autowired
	// private MacchinaRepository macchinaRepository;

	@Autowired
	private MockMvc mockMvc;


	@Test
	void contextLoads() {
		assertThat(machineController).isNotNull();
	}

	@Test
	public void listAllMacchine() throws Exception {
		// macchinaRepository.save(new Macchina("One"));

		mockMvc.perform(get("/machines"))
				.andDo(print())
				.andExpect(status().isOk());

	}


}
