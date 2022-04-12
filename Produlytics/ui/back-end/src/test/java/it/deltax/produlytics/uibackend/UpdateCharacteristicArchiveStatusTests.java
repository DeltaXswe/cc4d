package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.repositories.CharacteristicRepository;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test d'integrazione per le operazioni svolte dagli amministratori relative alla modifica dello stato di
 * archiviazione di una caratteristica
 */
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class UpdateCharacteristicArchiveStatusTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private CharacteristicRepository characteristicRepository;

	private static int deviceId;
	private static int characteristicId;

	/**
	 * Prepara il contesto di partenza, comune a tutti i test
	 */
	@BeforeEach
	private void prepareContext() {
		DeviceEntity device = this.deviceRepository.save(new DeviceEntity(
			"macchina",
			false,
			false,
			"a"
		));

		deviceId = device.getId();

		CharacteristicEntity characteristic = this.characteristicRepository.save(new CharacteristicEntity(
			deviceId,
			"temperatura",
			98d,
			-13d,
			true,
			0,
			true
		));

		characteristicId = characteristic.getId();
	}

	/**
	 * Pulisce i repository dai dati utilizzati dai test
	 */
	@AfterEach
	private void deleteAll() {
		this.characteristicRepository.deleteAll();
		this.deviceRepository.deleteAll();
	}

	@Test
	void contextLoads() {
		assertThat(this.deviceRepository).isNotNull();
		assertThat(this.characteristicRepository).isNotNull();
	}

	/**
	 * Testa la modifica dello stato di archiviazione di una macchina, impostando true come valore
	 * @throws Exception se l'operazione non va a buon fine
	 */
	@Test
	void setArchived() throws Exception {
		JSONObject body = new JSONObject()
			.put("archived", true);

		this.mockMvc.perform(put(
			"/admins/devices/" + deviceId + "/characteristics/" + characteristicId + "/archived"
			)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body.toString())
				.characterEncoding("utf-8")
			)
			.andDo(print())
			.andExpect(status().isNoContent());

		assertThat(this.characteristicRepository.findById(new CharacteristicEntityId(deviceId, characteristicId))
			.get()
			.getArchived()
		).isEqualTo(true);
	}

	/**
	 * Testa la modifica dello stato di archiviazione di una macchina, impostando false come valore
	 * @throws Exception se l'operazione non va a buon fine
	 */
	@Test
	void setUnarchived() throws Exception {
		JSONObject body = new JSONObject()
			.put("archived", false);

		this.mockMvc.perform(put(
					"/admins/devices/" + deviceId + "/characteristics/" + characteristicId + "/archived"
				)
					.contentType(MediaType.APPLICATION_JSON)
					.content(body.toString())
					.characterEncoding("utf-8")
			)
			.andDo(print())
			.andExpect(status().isNoContent());

		assertThat(this.characteristicRepository.findById(new CharacteristicEntityId(deviceId, characteristicId))
			.get()
			.getArchived()
		).isEqualTo(false);
	}

	/**
	 * Testa la modifica dello stato di una caratteristica inesistente
	 * @throws Exception se l'errore non viene rilevato
	 */
	@Test
	void characteristicNotFoundError() throws Exception {
		deleteAll();

		JSONObject body = new JSONObject()
			.put("archived", false);

		JSONObject response = new JSONObject()
			.put("errorCode", "characteristicNotFound");

		this.mockMvc.perform(put(
					"/admins/devices/" + deviceId + "/characteristics/" + characteristicId + "/archived"
				)
					.contentType(MediaType.APPLICATION_JSON)
					.content(body.toString())
					.characterEncoding("utf-8")
			)
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().json(response.toString()));

		prepareContext();
	}
}
