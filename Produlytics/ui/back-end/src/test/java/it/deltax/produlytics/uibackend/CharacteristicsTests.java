package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.devices.web.CharacteristicsController;
import it.deltax.produlytics.uibackend.repositories.CharacteristicRepository;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Test d'integrazione per le operazioni relative alle caratteristiche non archiviate di una macchine
 */
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class CharacteristicsTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private CharacteristicsController controller;

	@Autowired
	private CharacteristicRepository characteristicRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	private static int deviceId;
	private static int characteristic1Id;
	private static int characteristic2Id;
	private static int characteristic3Id;

	/**
	 * Prepara il contesto di partenza, comune a tutti i test
	 * @param deviceRepository lo strato di persistenza relativo alle macchine
	 * @param characteristicRepository lo strato di persistenza relativo alle caratteristiche
	 */
	@BeforeAll
	private static void prepareContext(
		@Autowired CharacteristicRepository characteristicRepository,
		@Autowired DeviceRepository deviceRepository
	) {
		deviceId = deviceRepository.save(new DeviceEntity(
			"macchina",
			false,
			false,
			"a"
		)).getId();

		characteristic1Id = characteristicRepository.save(new CharacteristicEntity(
			deviceId,
			"temperatura",
			98d,
			-13d,
			true,
			0,
			false
		)).getId();

		characteristic2Id = characteristicRepository.save(new CharacteristicEntity(
			deviceId,
			"pressione",
			100d,
			10d,
			true,
			0,
			false
		)).getId();

		characteristic3Id = characteristicRepository.save(new CharacteristicEntity(
			deviceId,
			"frequenza",
			100d,
			10d,
			true,
			0,
			true
		)).getId();
	}

	/**
	 * Pulisce i repository dai dati utilizzati dai test
	 * @param deviceRepository lo strato di persistenza relativo alle macchine
	 * @param characteristicRepository lo strato di persistenza relativo alle caratteristiche
	 */
	@AfterAll
	private static void deleteAll(
		@Autowired CharacteristicRepository characteristicRepository,
		@Autowired DeviceRepository deviceRepository
	) {
		characteristicRepository.deleteAll();
		deviceRepository.deleteAll();
	}

	@Test
	void contextLoads() {
		assertThat(this.controller).isNotNull();
		assertThat(this.characteristicRepository).isNotNull();
		assertThat(this.deviceRepository).isNotNull();
	}

	/**
	 * Testa l'ottenimento delle caratteristiche non archiviate di una macchina
	 * @throws Exception se la macchina non esiste o non vengono restituite le caratteristiche attese
	 */
	@Test
	void getUnarchivedCharacteristics() throws Exception {
		var characteristic1 = new JSONObject()
			.put("id", characteristic1Id)
			.put("name", "temperatura");

		var characteristic2 = new JSONObject()
			.put("id", characteristic2Id)
			.put("name", "pressione");

		var response = new JSONArray()
			.put(characteristic1)
			.put(characteristic2);

		this.mockMvc.perform(get("/devices/" + deviceId + "/characteristics"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json(response.toString()));
	}

	/**
	 * Testa l'ottenimento delle caratteristiche non archiviate di una macchina inesistente
	 * @throws Exception se non viene rilevato l'errore
	 */
	@Test
	void deviceNotFoundError() throws Exception {
		deleteAll(characteristicRepository, deviceRepository);

		this.mockMvc.perform(get("/devices/1/characteristics"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("{\"errorCode\":\"deviceNotFound\"}"));

		prepareContext(characteristicRepository, deviceRepository);
	}

	/**
	 * Testa l'ottenimento dei limiti tecnici di una caratteristica non archiviata
	 * @throws Exception se la caratteristica è inesistente, è archiviata o non vengono restituiti i limiti attesi
	 */
	@Test
	void getCharacteristicLimits() throws Exception {
		this.mockMvc.perform(get(
			"/devices/" + deviceId + "/characteristics/" + characteristic1Id + "/limits"
			))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string("{\"lowerLimit\":-13.0,\"upperLimit\":98.0,\"mean\":42.5}"));
	}

	/**
	 * Testa l'ottenimento dei limiti tecnici di una caratteristica inesistente
	 * @throws Exception se non viene rilevato l'errore
	 */
	@Test
	void characteristicNotFoundError() throws Exception {
		characteristicRepository.deleteAll();

		this.mockMvc.perform(get("/devices/" + deviceId + "/characteristics/1/limits"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("{\"errorCode\":\"characteristicNotFound\"}"));

		deleteAll(characteristicRepository, deviceRepository);
		prepareContext(characteristicRepository, deviceRepository);
	}

	/**
	 * Testa l'ottenimento dei limiti tecnici di una caratteristica archiviata
	 * @throws Exception se la caratteristica non esiste, non è archiviata o non viene rilevato l'errore
	 */
	@Test
	void characteristicArchivedError() throws Exception {
		this.mockMvc.perform(get(
			"/devices/" + deviceId + "/characteristics/" + characteristic3Id + "/limits"
			))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("{\"errorCode\":\"characteristicNotFound\"}"));
	}
}
