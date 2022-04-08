package it.deltax.produlytics.api;

import it.deltax.produlytics.api.detections.business.domain.queue.DetectionQueue;
import it.deltax.produlytics.api.detections.web.DetectionsController;
import it.deltax.produlytics.api.repositories.CharacteristicRepository;
import it.deltax.produlytics.api.repositories.DetectionRepository;
import it.deltax.produlytics.api.repositories.DeviceRepository;
import it.deltax.produlytics.persistence.*;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class DetectionsTest {
	@Autowired
	private DetectionsController detectionsController;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
	private CharacteristicRepository characteristicRepository;

	@Autowired
	private DetectionRepository detectionRepository;

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private DetectionQueue detectionQueue;

	@Test
	void contextLoads() {
		assert Objects.nonNull(this.detectionsController);
		assert Objects.nonNull(this.deviceRepository);
		assert Objects.nonNull(this.characteristicRepository);
		assert Objects.nonNull(this.detectionRepository);
	}

	int insertDummyCharacteristic(
		String name, boolean deviceArchived, boolean deviceDeactivated, boolean characteristicArchived
	) {
		DeviceEntity device = this.deviceRepository.save(new DeviceEntity(name,
			deviceArchived,
			deviceDeactivated,
			name
		));
		CharacteristicEntityId characteristicId = new CharacteristicEntityId(device.getId(), 1);
		CharacteristicEntity characteristic = this.characteristicRepository.save(new CharacteristicEntity(characteristicId,
			name,
			null,
			null,
			true,
			5,
			characteristicArchived
		));
		return characteristic.getId().getId();
	}

	@Test
	void testApiKeyInvalid() throws Exception {
		String ident = "1";
		int characteristicId = this.insertDummyCharacteristic(ident, false, false, false);

		String requestBody = new JSONObject().put("apiKey", "invalidApiKey")
			.put("characteristic", characteristicId)
			.put("value", 0)
			.toString();
		String requestResponse = new JSONObject().put("errorCode", "notAuthenticated").toString();

		mockMvc.perform(post("/detections").content(requestBody).contentType("application/json"))
			.andExpect(status().is(401))
			.andExpect(content().json(requestResponse));
	}

	@Test
	void testDeviceArchived() throws Exception {
		String ident = "2";
		int characteristicId = this.insertDummyCharacteristic(ident, true, false, false);

		String requestBody = new JSONObject().put("apiKey", ident)
			.put("characteristic", characteristicId)
			.put("value", 0)
			.toString();
		String requestResponse = new JSONObject().put("errorCode", "archived").toString();

		mockMvc.perform(post("/detections").content(requestBody).contentType("application/json"))
			.andExpect(status().is(410))
			.andExpect(content().json(requestResponse));
	}

	@Test
	void testDeviceDeactivated() throws Exception {
		String ident = "3";
		int characteristicId = this.insertDummyCharacteristic(ident, false, true, false);

		String requestBody = new JSONObject().put("apiKey", ident)
			.put("characteristic", characteristicId)
			.put("value", 0)
			.toString();
		String requestResponse = new JSONObject().put("errorCode", "archived").toString();

		mockMvc.perform(post("/detections").content(requestBody).contentType("application/json"))
			.andExpect(status().is(410))
			.andExpect(content().json(requestResponse));
	}

	@Test
	void testCharacteristicInvalid() throws Exception {
		String ident = "4";
		int characteristicId = this.insertDummyCharacteristic(ident, false, false, false);

		String requestBody = new JSONObject().put("apiKey", ident)
			.put("characteristic", characteristicId + 1)
			.put("value", 0)
			.toString();
		String requestResponse = new JSONObject().put("errorCode", "characteristicNotFound").toString();

		mockMvc.perform(post("/detections").content(requestBody).contentType("application/json"))
			.andExpect(status().is(404))
			.andExpect(content().json(requestResponse));
	}

	@Test
	void testCharacteristicArchived() throws Exception {
		String ident = "5";
		int characteristicId = this.insertDummyCharacteristic(ident, false, false, true);

		String requestBody = new JSONObject().put("apiKey", ident)
			.put("characteristicId", characteristicId)
			.put("value", 0)
			.toString();
		String requestResponse = new JSONObject().put("errorCode", "archived").toString();

		mockMvc.perform(post("/detections").content(requestBody).contentType("application/json"))
			.andExpect(status().is(410))
			.andExpect(content().json(requestResponse));
	}

	@Test
	void testOk() throws Exception {
		String ident = "6";
		int characteristicId = this.insertDummyCharacteristic(ident, false, false, false);
		int deviceId = this.deviceRepository.findByApiKey(ident).get().getId();
		this.detectionRepository.save(new DetectionEntity(new DetectionEntityId(Instant.now(),
			characteristicId,
			deviceId
		), 42d, false));

		String requestBody = new JSONObject().put("apiKey", ident)
			.put("characteristicId", characteristicId)
			.put("value", 43)
			.toString();

		mockMvc.perform(post("/detections").content(requestBody).contentType("application/json"))
			.andExpect(status().is(202))
			.andExpect(content().string(""));

		Thread.sleep(100);

		List<DetectionEntity> lastDetections = this.detectionRepository.findLastDetectionsById(deviceId,
			characteristicId,
			3
		);
		assert lastDetections.size() == 2;
		assert lastDetections.get(0).getValue() == 42d;
		assert lastDetections.get(1).getValue() == 43d;

	}

	@Test
	void testMarkOutlier() throws Exception {
		String ident = "7";
		DeviceEntity device = this.deviceRepository.save(new DeviceEntity(ident, false, false, "7"));
		CharacteristicEntityId characteristicId = new CharacteristicEntityId(device.getId(), 1);
		CharacteristicEntity characteristic = this.characteristicRepository.save(new CharacteristicEntity(characteristicId,
			ident,
			60d,
			0d,
			false,
			null,
			false
		));

		String requestBody = new JSONObject().put("apiKey", "7")
			.put("characteristicId", characteristic.getId().getId())
			.put("value", 70d)
			.toString();

		mockMvc.perform(post("/detections").content(requestBody).contentType("application/json"))
			.andExpect(status().is(202))
			.andExpect(content().string(""));

		Thread.sleep(100);

		List<DetectionEntity> lastDetections = this.detectionRepository.findLastDetectionsById(device.getId(),
			characteristic.getId().getId(),
			3
		);
		System.out.println("" + lastDetections.size());
		assert lastDetections.size() == 1;
		assert lastDetections.get(0).getOutlier();
	}
}
