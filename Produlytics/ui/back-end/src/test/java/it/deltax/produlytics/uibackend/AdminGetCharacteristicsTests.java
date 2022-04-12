package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.repositories.CharacteristicRepository;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import net.minidev.json.JSONArray;
import net.minidev.json.JSONObject;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test d'integrazione per le operazioni svolte dagli amministratori relative all'ottenimento
 * delle caratteristiche di una macchina
 * @author Alberto Lazati
 */
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class AdminGetCharacteristicsTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private CharacteristicRepository characteristicRepository;

	private static int deviceId;
	private static int characteristic1Id;
	private static int characteristic2Id;

	/**
	 * Prepara il contesto di partenza, comune a tutti i test
	 * @param deviceRepository lo strato di persistenza relativo alle macchine
	 * @param characteristicRepository lo strato di persistenza relativo alle caratteristiche
	 */
	@BeforeAll
	private static void prepareContext(
		@Autowired DeviceRepository deviceRepository,
		@Autowired CharacteristicRepository characteristicRepository
	) {
		DeviceEntity device = deviceRepository.save(new DeviceEntity(
			"macchina",
			false,
			false,
			"a"
		));

		deviceId = device.getId();

		CharacteristicEntity characteristic = characteristicRepository.save(new CharacteristicEntity(
			deviceId,
			"temperatura",
			98d,
			-13d,
			true,
			0,
			true
		));

		characteristic1Id = characteristic.getId();

		characteristic = characteristicRepository.save(new CharacteristicEntity(
			deviceId,
			"pressione",
			100d,
			10d,
			false,
			null,
			false
		));

		characteristic2Id = characteristic.getId();
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

	@Test
	void contextLoads() {
		assertThat(this.deviceRepository).isNotNull();
		assertThat(this.characteristicRepository).isNotNull();
	}

	/**
	 * Esegue l'ottenimento delle caratteristiche senza eseguire controlli
	 * @return il risultato dell'esecuzione su cui poter eseguire i controlli
	 * @throws Exception se l'ottenimento non va a buon fine
	 */
	private ResultActions performGetCharacteristics() throws Exception {
		return this.mockMvc.perform(get("/admins/devices/" + deviceId + "/characteristics"))
			.andDo(print());
	}

	/**
	 * Testa l'ottenimento di tutte le caratteristiche di una macchina
	 * @throws Exception se la macchina non esiste o non vengono restituite le caratteristiche attese
	 */
	@Test
	void getCharacteristics() throws Exception {
		JSONObject characteristic1 = new JSONObject();
		characteristic1.put("id", characteristic1Id);
		characteristic1.put("name", "temperatura");
		characteristic1.put("archived", true);

		JSONObject characteristic2 = new JSONObject();
		characteristic1.put("id", characteristic2Id);
		characteristic1.put("name", "pressione");
		characteristic1.put("archived", false);

		JSONArray response = new JSONArray();
		response.add(characteristic1);
		response.add(characteristic2);

		performGetCharacteristics()
			.andExpect(status().isOk())
			.andExpect(content().json(response.toJSONString()));
	}

	@Test
	void deviceNotFoundError() throws Exception {
		deleteAll(deviceRepository, characteristicRepository);

		JSONObject response = new JSONObject();
		response.put("errorCode", "deviceNotFound");

		performGetCharacteristics()
			.andExpect(status().isNotFound())
			.andExpect(content().json(response.toJSONString()));

		prepareContext(deviceRepository, characteristicRepository);
	}
}
