package it.deltax.produlytics.uibackend.integration;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.DetectionEntity;
import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.repositories.CharacteristicRepository;
import it.deltax.produlytics.uibackend.repositories.DetectionRepository;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONArray;
import org.json.JSONException;
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

import java.time.Instant;

/**
 * Test d'integrazione per le operazioni relative alle rilevazioni
 */
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class DetectionsTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private CharacteristicRepository characteristicRepository;

	@Autowired
	private DetectionRepository detectionRepository;

	private static int deviceId;
	private static int characteristicId;
	private static int archivedCharacteristicId;

	private final JSONObject detection1;
	private final JSONObject detection2;
	private final JSONObject detection3;
	private final JSONObject detection4;

	/**
	 * Il costruttore
	 * @throws JSONException se viene inserita una chiave duplicata nella rappresentazione JSON delle rilevazioni
	 */
	public DetectionsTests() throws JSONException {
		detection1 = new JSONObject()
			.put("creationTime", 1)
			.put("value", 100d)
			.put("outlier", false);

		detection2 = new JSONObject()
			.put("creationTime", 2)
			.put("value", 200d)
			.put("outlier", false);

		detection3 = new JSONObject()
			.put("creationTime", 3)
			.put("value", 300d)
			.put("outlier", false);

		detection4 = new JSONObject()
			.put("creationTime", 4)
			.put("value", 400d)
			.put("outlier", false);
	}

	/**
	 * Prepara il contesto di partenza, comune a tutti i test
	 * @param deviceRepository lo strato di persistenza relativo alle macchine
	 * @param characteristicRepository lo strato di persistenza relativo alle caratteristiche
	 * @param detectionRepository lo strato di persistenza relativo alle rilevazioni
	 */
	@BeforeAll
	private static void prepareContext(
		@Autowired DeviceRepository deviceRepository,
		@Autowired CharacteristicRepository characteristicRepository,
		@Autowired DetectionRepository detectionRepository
	) {
		deviceId = deviceRepository.save(new DeviceEntity(
			"macchina",
			false,
			false,
			"x"
		)).getId();

		characteristicId = characteristicRepository.save(new CharacteristicEntity(
			deviceId,
			"temperatura",
			98d,
			-13d,
			true,
			0,
			false
		)).getId();

		archivedCharacteristicId = characteristicRepository.save(new CharacteristicEntity(
			deviceId,
			"temperatura",
			98d,
			-13d,
			true,
			0,
			true
		)).getId();

		for (int i = 1; i < 5; ++i) {
			detectionRepository.save(new DetectionEntity(
				Instant.ofEpochMilli(i),
				characteristicId,
				deviceId,
				100d * i,
				false
			));
		}
	}

	/**
	 * Pulisce i repository dai dati utilizzati dai test
	 * @param deviceRepository lo strato di persistenza relativo alle macchine
	 * @param characteristicRepository lo strato di persistenza relativo alle caratteristiche
	 * @param detectionRepository lo strato di persistenza relativo alle rilevazioni
	 */
	@AfterAll
	private static void deleteAll(
		@Autowired DeviceRepository deviceRepository,
		@Autowired CharacteristicRepository characteristicRepository,
		@Autowired DetectionRepository detectionRepository
	) {
		detectionRepository.deleteAll();
		characteristicRepository.deleteAll();
		deviceRepository.deleteAll();
	}

	@Test
	void contextLoads() {
		assertThat(this.deviceRepository).isNotNull();
		assertThat(this.characteristicRepository).isNotNull();
		assertThat(this.detectionRepository).isNotNull();
	}

	/**
	 * Testa l'ottenimento delle rilevazioni di una caratteristica senza applicare filtri di ricerca
	 * @throws Exception se la caratteristica è inesistente, archiviata o si ottiene un risultato diverso da
	 * quello atteso
	 */
	@Test
	void testGetWithNoFilter() throws Exception {
		JSONArray detections = new JSONArray()
			.put(detection1)
			.put(detection2)
			.put(detection3)
			.put(detection4);

		JSONObject response = new JSONObject()
			.put("detections", detections)
			.put("nextOld", null)
			.put("nextNew", 4);

		this.mockMvc.perform(get(
				"/devices/" + deviceId + "/characteristics/" + characteristicId + "/detections"
			))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json(response.toString()));
	}

	/**
	 * Testa l'ottenimento delle rilevazioni di una caratteristica con il limite
	 * @throws Exception se la caratteristica è inesistente, archiviata o si ottiene un risultato diverso da
	 * quello atteso
	 */
	@Test
	void testGetWithLimit() throws Exception {
		JSONArray detections = new JSONArray()
			.put(detection3)
			.put(detection4);

		JSONObject response = new JSONObject()
			.put("detections", detections)
			.put("nextOld", 3)
			.put("nextNew", 4);

		this.mockMvc.perform(get(
				"/devices/" + deviceId + "/characteristics/" + characteristicId + "/detections?limit=2"
			))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json(response.toString()));
	}

	/**
	 * Testa l'ottenimento delle rilevazioni di una caratteristica meno recenti di un determinato istante
	 * @throws Exception se la caratteristica è inesistente, archiviata o si ottiene un risultato diverso da
	 * quello atteso
	 */
	@Test
	void testGetWithOlder() throws Exception {
		JSONArray detections = new JSONArray()
			.put(detection1)
			.put(detection2)
			.put(detection3);

		JSONObject response = new JSONObject()
			.put("detections", detections)
			.put("nextOld", null)
			.put("nextNew", 3);

		this.mockMvc.perform(get(
				"/devices/"
					+ deviceId
					+ "/characteristics/"
					+ characteristicId
					+ "/detections?olderThan=4"
			))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json(response.toString()));
	}

	/**
	 * Testa l'ottenimento delle rilevazioni di una caratteristica più recenti di un determinato istante
	 * @throws Exception se la caratteristica è inesistente, archiviata o si ottiene un risultato diverso da
	 * quello atteso
	 */
	@Test
	void testGetWithNewer() throws Exception {
		JSONArray detections = new JSONArray()
			.put(detection2)
			.put(detection3)
			.put(detection4);

		JSONObject response = new JSONObject()
			.put("detections", detections)
			.put("nextOld", 2)
			.put("nextNew", 4);

		this.mockMvc.perform(get(
				"/devices/"
					+ deviceId
					+ "/characteristics/"
					+ characteristicId
					+ "/detections?newerThan=1"
			))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().json(response.toString()));
	}

	/**
	 * Testa l'ottenimento delle rilevazioni di una caratteristica, facendo restituire una lista vuota invertendo
	 * l'ordine di olerThan e newerThan
	 * @throws Exception se la caratteristica è inesistente, archiviata o si ottiene un risultato diverso da
	 * quello atteso
	 */
	@Test
	void testGetEmpty() throws Exception {
		this.mockMvc.perform(get(
				"/devices/"
					+ deviceId
					+ "/characteristics/"
					+ characteristicId
					+ "/detections?newerThan=7&olderThan=4"
			))
			.andDo(print())
			.andExpect(status().isOk());
	}

	/**
	 * Testa l'ottenimento delle rilevazioni di una caratteristica inesistente
	 * @throws Exception se non viene rilevato l'errore
	 */
	@Test
	void testCharacteristicNotFoundError() throws Exception {
		deleteAll(deviceRepository, characteristicRepository, detectionRepository);

		JSONObject response = new JSONObject()
			.put("errorCode", "characteristicNotFound");

		this.mockMvc.perform(get(
				"/devices/" + deviceId + "/characteristics/1/detections"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().json(response.toString()));

		prepareContext(deviceRepository, characteristicRepository, detectionRepository);
	}

	/**
	 * Testa l'ottenimento delle rilevazioni di una caratteristica archiviata
	 * @throws Exception se non viene rilevato l'errore
	 */
	@Test
	void testCharacteristicArchivedError() throws Exception {
		JSONObject response = new JSONObject()
			.put("errorCode", "characteristicNotFound");

		this.mockMvc.perform(get(
				"/devices/" + deviceId + "/characteristics/" + archivedCharacteristicId + "/detections"))
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().json(response.toString()));
	}
}
