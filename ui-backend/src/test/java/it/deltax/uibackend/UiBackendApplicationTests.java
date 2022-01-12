package it.deltax.uibackend;

import static org.assertj.core.api.Assertions.assertThat;

import it.deltax.uibackend.web.MachinesController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UiBackendApplicationTests {

	@Autowired
	private MachinesController machineController;

	@Test
	void contextLoads() {
		assertThat(machineController).isNotNull();
	}

}
