package it.deltax.produlytics.uibackend.integration;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.repositories.CharacteristicRepository;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
 * Test d'integrazione per le operazioni svolte dagli amministratori relative alla modifica di una caratteristica
 */
@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class UpdateCharacteristicTests {
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private CharacteristicRepository characteristicRepository;

	private static int deviceId;
	private static int characteristicId;
	private static int archivedCharacteristicId;

	/**
	 * Prepara il contesto di partenza, comune a tutti i test
	 */
	@BeforeEach
	private void prepareContext() {
		deviceId = this.deviceRepository.save(new DeviceEntity(
			"macchina",
			false,
			false,
			"a"
		)).getId();

		characteristicId = this.characteristicRepository.save(new CharacteristicEntity(
			deviceId,
			"temperatura",
			98d,
			-13d,
			true,
			0,
			false
		)).getId();

		archivedCharacteristicId = this.characteristicRepository.save(new CharacteristicEntity(
			deviceId,
			"pressione",
			50d,
			-10d,
			false,
			0,
			true
		)).getId();
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
	 * Testa la modifica di una caratteristica non impostando l'autoAdjust
	 * @throws Exception se la caratteristica non esiste, verrebbe duplicata dopo la modifica, vengono inseriti dei
	 * valori non validi o la caratteristica non viene modificata correttamente
	 */
	@Test
	void updateCharacteristicWithNoAutoAdjust() throws Exception {
		JSONObject body = new JSONObject()
			.put("name", "frequenza")
			.put("lowerLimit", 10d)
			.put("upperLimit", 100d)
			.put("autoAdjust", false);

		this.mockMvc.perform(put("/admin/devices/" + deviceId + "/characteristics/" + characteristicId)
					.contentType(MediaType.APPLICATION_JSON)
					.content(body.toString())
					.characterEncoding("utf-8")
			)
			.andDo(print())
			.andExpect(status().isNoContent());

		CharacteristicEntity characteristic = this.characteristicRepository
			.findById(new CharacteristicEntityId(deviceId, characteristicId)).get();

		assertThat(characteristic.getName()).isEqualTo("frequenza");
		assertThat(characteristic.getLowerLimit()).isEqualTo(10d);
		assertThat(characteristic.getUpperLimit()).isEqualTo(100d);
		assertThat(characteristic.getAutoAdjust()).isEqualTo(false);
	}

	/**
	 * Testa la modifica di una caratteristica impostando l'autoAdjust
	 * @throws Exception se la caratteristica non esiste, verrebbe duplicata dopo la modifica, vengono inseriti dei
	 * valori non validi o la caratteristica non viene modificata correttamente
	 */
	@Test
	void updateCharacteristicWithAutoAdjust() throws Exception {
		JSONObject body = new JSONObject()
			.put("name", "frequenza")
			.put("autoAdjust", true)
			.put("sampleSize", 10);

		this.mockMvc.perform(put("/admin/devices/" + deviceId + "/characteristics/" + characteristicId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body.toString())
				.characterEncoding("utf-8")
			)
			.andDo(print())
			.andExpect(status().isNoContent());

		CharacteristicEntity characteristic = this.characteristicRepository
			.findById(new CharacteristicEntityId(deviceId, characteristicId)).get();

		assertThat(characteristic.getName()).isEqualTo("frequenza");
		assertThat(characteristic.getAutoAdjust()).isEqualTo(true);
		assertThat(characteristic.getSampleSize()).isEqualTo(10);
	}

	/**
	 * Testa la modifica di una caratteristica archiviata
	 * @throws Exception se la caratteristica non esiste, verrebbe duplicata dopo la modifica, vengono inseriti dei
	 * valori non validi o la caratteristica non viene modificata correttamente
	 */
	@Test
	void updateArchivedCharacteristic() throws Exception {
		JSONObject body = new JSONObject()
			.put("name", "frequenza")
			.put("lowerLimit", 10d)
			.put("upperLimit", 100d)
			.put("autoAdjust", false);

		this.mockMvc.perform(put(
			"/admin/devices/" + deviceId + "/characteristics/" + archivedCharacteristicId
				)
					.contentType(MediaType.APPLICATION_JSON)
					.content(body.toString())
					.characterEncoding("utf-8")
			)
			.andDo(print())
			.andExpect(status().isNoContent());

		CharacteristicEntity characteristic = this.characteristicRepository
			.findById(new CharacteristicEntityId(deviceId, archivedCharacteristicId)).get();

		assertThat(characteristic.getName()).isEqualTo("frequenza");
		assertThat(characteristic.getAutoAdjust()).isEqualTo(false);
		assertThat(characteristic.getArchived()).isEqualTo(true);
	}

	/**
	 * Testa la modifica di una caratteristica inserendo il nome di una caratteristica già esistente
	 * @throws Exception se la caratteristica non esiste o non viene rilevato l'errore
	 */
	@Test
	void updateCharacteristicDuplicateError() throws Exception {
		JSONObject body = new JSONObject()
			.put("name", "pressione")
			.put("autoAdjust", true)
			.put("sampleSize", 10);

		JSONObject response = new JSONObject()
			.put("errorCode", "duplicateCharacteristicName");

		this.mockMvc.perform(put(
					"/admin/devices/" + deviceId + "/characteristics/" + characteristicId
				)
					.contentType(MediaType.APPLICATION_JSON)
					.content(body.toString())
					.characterEncoding("utf-8")
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().json(response.toString()));
	}

	/**
	 * Testa la modifica di una caratteristica senza autoAdjust, ma senza limiti tecnici
	 * @throws Exception se la caratteristica non esiste, verrebbe duplicata dopo la modifica o non viene rilevato
	 * l'errore
	 */
	@Test
	void updateCharacteristicNoLimitsError() throws Exception {
		JSONObject body = new JSONObject()
			.put("name", "frequenza")
			.put("autoAdjust", false);

		JSONObject response = new JSONObject()
			.put("errorCode", "invalidValues");

		this.mockMvc.perform(put(
					"/admin/devices/" + deviceId + "/characteristics/" + characteristicId
				)
					.contentType(MediaType.APPLICATION_JSON)
					.content(body.toString())
					.characterEncoding("utf-8")
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().json(response.toString()));
	}

	/**
	 * Testa la modifica di una caratteristica con autoAdjust, ma senza sampleSize
	 * @throws Exception se la caratteristica non esiste, verrebbe duplicata dopo la modifica o non viene rilevato
	 * l'errore
	 */
	@Test
	void updateCharacteristicNoSampleSizeError() throws Exception {
		JSONObject body = new JSONObject()
			.put("name", "frequenza")
			.put("autoAdjust", true);

		JSONObject response = new JSONObject()
			.put("errorCode", "invalidValues");

		this.mockMvc.perform(put(
					"/admin/devices/" + deviceId + "/characteristics/" + characteristicId
				)
					.contentType(MediaType.APPLICATION_JSON)
					.content(body.toString())
					.characterEncoding("utf-8")
			)
			.andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().json(response.toString()));
	}

	/**
	 * Testa la modifica di una caratteristica inesistente
	 * @throws Exception se non viene rilevato l'errore
	 */
	@Test
	void updateCharacteristicNotFoundError() throws Exception {
		deleteAll();

		JSONObject body = new JSONObject()
			.put("name", "frequenza")
			.put("autoAdjust", true)
			.put("sampleSize", 10);

		JSONObject response = new JSONObject()
			.put("errorCode", "characteristicNotFound");

		this.mockMvc.perform(put(
					"/admin/devices/" + deviceId + "/characteristics/" + characteristicId
				)
					.contentType(MediaType.APPLICATION_JSON)
					.content(body.toString())
					.characterEncoding("utf-8")
			)
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().json(response.toString()));
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
			"/admin/devices/" + deviceId + "/characteristics/" + characteristicId + "/archived"
			)
				.contentType(MediaType.APPLICATION_JSON)
				.content(body.toString())
				.characterEncoding("utf-8")
			)
			.andDo(print())
			.andExpect(status().isNoContent());

		assertThat(this.characteristicRepository
			.findById(new CharacteristicEntityId(deviceId, characteristicId))
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
					"/admin/devices/" + deviceId + "/characteristics/" + characteristicId + "/archived"
				)
					.contentType(MediaType.APPLICATION_JSON)
					.content(body.toString())
					.characterEncoding("utf-8")
			)
			.andDo(print())
			.andExpect(status().isNoContent());

		assertThat(this.characteristicRepository
			.findById(new CharacteristicEntityId(deviceId, characteristicId))
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
					"/admin/devices/" + deviceId + "/characteristics/" + characteristicId + "/archived"
				)
					.contentType(MediaType.APPLICATION_JSON)
					.content(body.toString())
					.characterEncoding("utf-8")
			)
			.andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().json(response.toString()));
	}
}
