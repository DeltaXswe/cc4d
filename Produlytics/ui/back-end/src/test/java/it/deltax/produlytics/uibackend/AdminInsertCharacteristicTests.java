package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.repositories.CharacteristicRepository;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import net.minidev.json.JSONObject;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test d'integrazione per le operazioni svolte dagli amministratori relative all'inserimento di una nuova
 * caratteristica
 */
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class AdminInsertCharacteristicTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private CharacteristicRepository characteristicRepository;

	private static int deviceId;

	/**
	 * Prepara il contesto di partenza, comune a tutti i test
	 * @param deviceRepository lo strato di persistenza relativo alle macchine
	 */
	@BeforeAll
	private static void prepareContext(@Autowired DeviceRepository deviceRepository) {
		DeviceEntity device = deviceRepository.save(new DeviceEntity(
			"macchina",
			false,
			false,
			"a"
		));

		deviceId = device.getId();
	}

	/**
	 * Pulisce i repository dai dati utilizzati dai test
	 * @param deviceRepository lo strato di persistenza relativo alle macchine
	 * @param characteristicRepository lo strato di persistenza relativo alle caratteristiche
	 */
	@AfterAll
	private static void deleteAll(
		@Autowired DeviceRepository deviceRepository,
		@Autowired CharacteristicRepository characteristicRepository
	) {
		characteristicRepository.deleteAll();
		deviceRepository.deleteAll();
	}

	/**
	 * Pulisce le caratteristiche inserite durante i test
	 */
	@BeforeEach
	private void cleanCharacteristics() {
		this.characteristicRepository.deleteAll();
	}

	@Test
	void contextLoads() {
		assertThat(this.deviceRepository).isNotNull();
		assertThat(this.characteristicRepository).isNotNull();
	}

	/**
	 * Esegue l'inserimento di una nuova caratteristica priva di limiti tecnici, senza eseguire controlli
	 * @return il risultato dell'esecuzione su cui poter eseguire i controlli
	 * @throws Exception se l'inserimento fallisce
	 */
	private ResultActions performInsertSmallCharacteristic() throws Exception {
		JSONObject body = new JSONObject();
		body.put("name", "pressione");
		body.put("autoAdjust", "true");
		body.put("sampleSize", 5);
		body.put("archived", "false");

		return this.mockMvc.perform(post("/admins/devices/" + deviceId + "/characteristics")
			.contentType(MediaType.APPLICATION_JSON)
			.content(body.toString())
			.characterEncoding("utf-8")
		);
	}

	/**
	 * Testa il corretto inserimento di una nuova caratteristica con autoAdjust e senza limiti tecnici
	 * @throws Exception se l'inserimento non va a buon fine
	 */
	@Test
	void insertCharacteristicWithAutoAdjustAndNoLimits() throws Exception {
		performInsertSmallCharacteristic()
			.andDo(print())
			.andExpect(status().isOk());
	}

	/**
	 * Testa il corretto inserimento di una nuova caratteristica con autoAdjust e con limiti tecnici
	 * @throws Exception se l'inserimento non va a buon fine
	 */
	@Test
	void insertCharacteristicWithAutoAdjustAndLimits() throws Exception {
		JSONObject body = new JSONObject();
		body.put("name", "pressione");
		body.put("upperLimit", 98d);
		body.put("lowerLimit", -13d);
		body.put("autoAdjust", "true");
		body.put("sampleSize", 0);
		body.put("archived", "false");

		this.mockMvc.perform(post("/admins/devices/" + deviceId + "/characteristics")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body.toString())
				.characterEncoding("utf-8")
			)
			.andDo(print())
			.andExpect(status().isOk());
	}

	/**
	 * Testa il corretto inserimento di una nuova caratteristica senza autoAdjust
	 * @throws Exception se l'inserimento non va a buon fine
	 */
	@Test
	void insertCharacteristicWithNoAutoAdjustAndLimits() throws Exception {
		JSONObject body = new JSONObject();
		body.put("name", "pressione");
		body.put("upperLimit", 98d);
		body.put("lowerLimit", -13d);
		body.put("autoAdjust", "false");
		body.put("archived", "false");

		this.mockMvc.perform(post("/admins/devices/" + deviceId + "/characteristics")
			.contentType(MediaType.APPLICATION_JSON)
			.content(body.toString())
			.characterEncoding("utf-8")
		)
			.andDo(print())
			.andExpect(status().isOk());
	}

	/**
	 * Testa l'inserimento di una nuova caratteristica senza autoAdjust e senza limiti tecnici
	 * @throws Exception non viene rilevato l'errore
	 */
	@Test
	void insertCharacteristicWithNoAutoAdjustAndNoLimitsError() throws Exception {
		JSONObject body = new JSONObject();
		body.put("name", "pressione");
		body.put("autoAdjust", "false");
		body.put("archived", "false");

		this.mockMvc.perform(post("/admins/devices/" + deviceId + "/characteristics")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body.toString())
				.characterEncoding("utf-8")
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	/**
	 * Testa il corretto inserimento di una nuova caratteristica con autoAdjust, ma senza sampleSize
	 * @throws Exception se l'inserimento non va a buon fine
	 */
	@Test
	void insertCharacteristicWithAutoAdjustAndNoSampleSize() throws Exception {
		JSONObject body = new JSONObject();
		body.put("name", "pressione");
		body.put("autoAdjust", "true");
		body.put("archived", "false");

		this.mockMvc.perform(post("/admins/devices/" + deviceId + "/characteristics")
				.contentType(MediaType.APPLICATION_JSON)
				.content(body.toString())
				.characterEncoding("utf-8")
			)
			.andDo(print())
			.andExpect(status().isBadRequest());
	}

	/**
	 * Testa l'inserimento di una caratteristica già esistente
	 * @throws Exception non viene rilevato l'errore
	 */
	@Test
	void duplicateError() throws Exception {
		JSONObject response = new JSONObject();
		response.put("errorCode", "duplicateCharacteristicName");

		performInsertSmallCharacteristic();

		performInsertSmallCharacteristic()
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().json(response.toJSONString()));
	}

	/**
	 * Testa l'inserimento di una caratteristica in una macchina inesistente
	 * @throws Exception non viene rilevato l'errore
	 */
	@Test
	void deviceNotFoundError() throws Exception {
		this.deviceRepository.deleteAll();

		JSONObject response = new JSONObject();
		response.put("errorCode", "deviceNotFound");

		performInsertSmallCharacteristic()
			.andExpect(status().isNotFound())
			.andExpect(content().json(response.toJSONString()));

		prepareContext(deviceRepository);
	}
}
