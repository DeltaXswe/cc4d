package it.deltax.produlytics.uibackend.integration;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import it.deltax.produlytics.persistence.DeviceEntity;
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
	private CharacteristicRepository characteristicRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	private static int deviceId;
	private static int characteristic1Id;
	private static int characteristic2Id;
	private static int archivedCharacteristicId;

	private static int archivedDeviceId;
	private static int archivedDeviceCharacteristicId;

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

		archivedCharacteristicId = characteristicRepository.save(new CharacteristicEntity(
			deviceId,
			"frequenza",
			100d,
			10d,
			true,
			0,
			true
		)).getId();


		archivedDeviceId = deviceRepository.save(new DeviceEntity(
			"macchina",
			true,
			false,
			"a"
		)).getId();

		archivedDeviceCharacteristicId = characteristicRepository.save(new CharacteristicEntity(
			archivedDeviceId,
			"temperatura",
			98d,
			-13d,
			true,
			0,
			false
		)).getId();
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
		assertThat(this.characteristicRepository).isNotNull();
		assertThat(this.deviceRepository).isNotNull();
	}

	/**
	 * Testa l'ottenimento delle caratteristiche non archiviate di una macchina
	 * @throws Exception se la macchina non esiste, è archiviata o non vengono restituite le caratteristiche attese
	 */
	@Test
	void testGetUnarchivedCharacteristics() throws Exception {
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
	 * Testa l'ottenimento delle caratteristiche non archiviate di una macchina che possiede solo caratteristiche
	 * archiviate
	 * @throws Exception se viene ottenuta qualche caratteristica, la macchina non esiste o è archiviata
	 */
	@Test
	void testGetEmptyCharacteristics() throws Exception {
		characteristicRepository.deleteById(new CharacteristicEntityId(deviceId, characteristic1Id));
		characteristicRepository.deleteById(new CharacteristicEntityId(deviceId, characteristic2Id));

		JSONArray response = new JSONArray();

		this.mockMvc.perform(get("/devices/" + deviceId + "/characteristics"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json(response.toString()));

		deleteAll(deviceRepository, characteristicRepository);
		prepareContext(deviceRepository, characteristicRepository);
	}

	/**
	 * Testa l'ottenimento delle caratteristiche di una macchina archiviata
	 * @throws Exception se non viene rilevato l'errore
	 */
	@Test
	void testGetFromArchivedDeviceError() throws Exception {
		JSONObject response = new JSONObject()
			.put("errorCode", "deviceNotFound");

		this.mockMvc.perform(get("/devices/" + archivedDeviceId + "/characteristics"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().json(response.toString()));
	}

	/**
	 * Testa l'ottenimento delle caratteristiche non archiviate di una macchina inesistente
	 * @throws Exception se non viene rilevato l'errore
	 */
	@Test
	void testDeviceNotFoundError() throws Exception {
		deleteAll(deviceRepository, characteristicRepository);

		JSONObject response = new JSONObject()
			.put("errorCode", "deviceNotFound");

		this.mockMvc.perform(get("/devices/1/characteristics"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().json(response.toString()));

		prepareContext(deviceRepository, characteristicRepository);
	}

	/**
	 * Testa l'ottenimento dei limiti tecnici di una caratteristica non archiviata
	 * @throws Exception se la caratteristica è inesistente, è archiviata o non vengono restituiti i limiti attesi
	 */
	@Test
	void testGetCharacteristicLimits() throws Exception {
		JSONObject response = new JSONObject()
			.put("lowerLimit", -13d)
			.put("upperLimit", 98d)
			.put("mean", 42.5);

		this.mockMvc.perform(get(
			"/devices/" + deviceId + "/characteristics/" + characteristic1Id + "/limits"
			))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json(response.toString()));
	}

	/**
	 * Testa l'ottenimento dei limiti tecnici di una caratteristica inesistente
	 * @throws Exception se non viene rilevato l'errore
	 */
	@Test
	void testCharacteristicNotFoundError() throws Exception {
		characteristicRepository.deleteAll();

		JSONObject response = new JSONObject()
			.put("errorCode", "characteristicNotFound");

		this.mockMvc.perform(get("/devices/" + deviceId + "/characteristics/1/limits"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().json(response.toString()));

		deleteAll(deviceRepository, characteristicRepository);
		prepareContext(deviceRepository, characteristicRepository);
	}

	/**
	 * Testa l'ottenimento dei limiti tecnici di una caratteristica archiviata
	 * @throws Exception se la caratteristica non esiste, non è archiviata o non viene rilevato l'errore
	 */
	@Test
	void testCharacteristicArchivedError() throws Exception {
		JSONObject response = new JSONObject()
			.put("errorCode", "characteristicNotFound");

		this.mockMvc.perform(get(
			"/devices/" + deviceId + "/characteristics/" + archivedCharacteristicId + "/limits"
			))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().json(response.toString()));
	}

	/**
	 * Testa l'ottenimento dei limiti tecnici di una caratteristica non archiviata di una macchina archiviata
	 * @throws Exception se non viene rilevato l'errore
	 */
	@Test
	void testDeviceArchivedError() throws Exception {
		JSONObject response = new JSONObject()
			.put("errorCode", "characteristicNotFound");

		this.mockMvc.perform(get("/devices/"
				+ archivedDeviceId
				+ "/characteristics/"
				+ archivedDeviceCharacteristicId
				+ "/limits"
			))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().json(response.toString()));
	}
}
