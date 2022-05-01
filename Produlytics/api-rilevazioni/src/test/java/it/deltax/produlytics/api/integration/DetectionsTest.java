package it.deltax.produlytics.api.integration;

import io.reactivex.rxjava3.plugins.RxJavaPlugins;
import it.deltax.produlytics.api.detections.business.domain.queue.DetectionQueue;
import it.deltax.produlytics.api.detections.web.DetectionsController;
import it.deltax.produlytics.api.repositories.CharacteristicRepository;
import it.deltax.produlytics.api.repositories.DetectionRepository;
import it.deltax.produlytics.api.repositories.DeviceRepository;
import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.DetectionEntity;
import it.deltax.produlytics.persistence.DeviceEntity;
import java.time.Instant;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingDeque;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class DetectionsTest {
  @Autowired private DetectionsController detectionsController;

  @Autowired private DeviceRepository deviceRepository;

  @Autowired private CharacteristicRepository characteristicRepository;

  @Autowired private DetectionRepository detectionRepository;

  @Autowired private MockMvc mockMvc;

  @Autowired private DetectionQueue detectionQueue;

  @Test
  void contextLoads() {
    assert Objects.nonNull(this.detectionsController);
    assert Objects.nonNull(this.deviceRepository);
    assert Objects.nonNull(this.characteristicRepository);
    assert Objects.nonNull(this.detectionRepository);
  }

  void insertDummyCharacteristic(
      String ident,
      boolean deviceArchived,
      boolean deviceDeactivated,
      boolean characteristicArchived) {
    DeviceEntity device =
        this.deviceRepository.save(
            new DeviceEntity(
                "device" + ident, deviceArchived, deviceDeactivated, "apiKey" + ident));

    this.characteristicRepository.save(
        new CharacteristicEntity(
            device.getId(), "characteristic" + ident, null, null, true, 5, characteristicArchived));
  }

  void capturingRxJavaExceptions(ThrowableRunnable runnable) {
    LinkedBlockingDeque<Throwable> errors = new LinkedBlockingDeque<>();
    RxJavaPlugins.setErrorHandler(errors::add);

    try {
      runnable.run();
    } catch (Exception exception) {
      throw new RuntimeException(exception);
    } finally {
      RxJavaPlugins.setErrorHandler(null);
    }

    if (!errors.isEmpty()) {
      throw new RuntimeException(
          "Expected no errors but RxJavaPlugins received " + Arrays.toString(errors.toArray()));
    }
  }

  @Test
  void testApiKeyInvalid() throws Exception {
    String ident = "1";
    this.insertDummyCharacteristic(ident, false, false, false);

    String requestBody =
        new JSONObject()
            .put("apiKey", "invalidApiKey")
            .put("characteristic", "characteristic" + ident)
            .put("value", 0)
            .toString();
    String requestResponse = new JSONObject().put("errorCode", "notAuthenticated").toString();

    mockMvc
        .perform(post("/detections").content(requestBody).contentType("application/json"))
        .andExpect(status().is(401))
        .andExpect(content().json(requestResponse));
  }

  @Test
  void testDeviceArchived() throws Exception {
    String ident = "2";
    this.insertDummyCharacteristic(ident, true, false, false);

    String requestBody =
        new JSONObject()
            .put("apiKey", "apiKey" + ident)
            .put("characteristic", "characteristic" + ident)
            .put("value", 0)
            .toString();
    String requestResponse = new JSONObject().put("errorCode", "archived").toString();

    mockMvc
        .perform(post("/detections").content(requestBody).contentType("application/json"))
        .andExpect(status().is(410))
        .andExpect(content().json(requestResponse));
  }

  @Test
  void testDeviceDeactivated() throws Exception {
    String ident = "3";
    this.insertDummyCharacteristic(ident, false, true, false);

    String requestBody =
        new JSONObject()
            .put("apiKey", "apiKey" + ident)
            .put("characteristic", "characteristic" + ident)
            .put("value", 0)
            .toString();
    String requestResponse = new JSONObject().put("errorCode", "archived").toString();

    mockMvc
        .perform(post("/detections").content(requestBody).contentType("application/json"))
        .andExpect(status().is(410))
        .andExpect(content().json(requestResponse));
  }

  @Test
  void testCharacteristicInvalid() throws Exception {
    String ident = "4";
    this.insertDummyCharacteristic(ident, false, false, false);

    String requestBody =
        new JSONObject()
            .put("apiKey", "apiKey" + ident)
            .put("characteristic", "characteristicInvalid" + ident)
            .put("value", 0)
            .toString();
    String requestResponse = new JSONObject().put("errorCode", "characteristicNotFound").toString();

    mockMvc
        .perform(post("/detections").content(requestBody).contentType("application/json"))
        .andExpect(status().is(404))
        .andExpect(content().json(requestResponse));
  }

  @Test
  void testCharacteristicArchived() throws Exception {
    String ident = "5";
    this.insertDummyCharacteristic(ident, false, false, true);

    String requestBody =
        new JSONObject()
            .put("apiKey", "apiKey" + ident)
            .put("characteristic", "characteristic" + ident)
            .put("value", 0)
            .toString();
    String requestResponse = new JSONObject().put("errorCode", "archived").toString();

    mockMvc
        .perform(post("/detections").content(requestBody).contentType("application/json"))
        .andExpect(status().is(410))
        .andExpect(content().json(requestResponse));
  }

  @Test
  void testDetectionAccepted() {
    capturingRxJavaExceptions(
        () -> {
          String ident = "6";
          this.insertDummyCharacteristic(ident, false, false, false);
          int deviceId = this.deviceRepository.findByApiKey("apiKey" + ident).get().getId();
          int characteristicId =
              this.characteristicRepository
                  .findByDeviceIdAndName(deviceId, "characteristic" + ident)
                  .get()
                  .getId();

          String requestBody =
              new JSONObject()
                  .put("apiKey", "apiKey" + ident)
                  .put("characteristic", "characteristic" + ident)
                  .put("value", 43)
                  .toString();

          mockMvc
              .perform(post("/detections").content(requestBody).contentType("application/json"))
              .andExpect(status().is(202))
              .andExpect(content().string(""));

          Thread.sleep(100);

          List<DetectionEntity> lastDetections =
              this.detectionRepository.findLastDetectionsById(deviceId, characteristicId, 3);
          assert lastDetections.size() == 1;
          assert lastDetections.get(0).getValue() == 43d;
          assert !lastDetections.get(0).getOutlier();
        });
  }

  @Test
  void testMarkOutlier() {
    capturingRxJavaExceptions(
        () -> {
          String ident = "7";
          DeviceEntity device =
              this.deviceRepository.save(
                  new DeviceEntity("device" + ident, false, false, "apiKey" + ident));
          CharacteristicEntity characteristic =
              this.characteristicRepository.save(
                  new CharacteristicEntity(
                      device.getId(), "characteristic" + ident, 60d, 0d, false, null, false));

          this.detectionRepository.save(
              new DetectionEntity(
                  Instant.now(), characteristic.getId(), device.getId(), 42d, false));

          String requestBody =
              new JSONObject()
                  .put("apiKey", "apiKey" + ident)
                  .put("characteristic", "characteristic" + ident)
                  .put("value", 70d)
                  .toString();

          mockMvc
              .perform(post("/detections").content(requestBody).contentType("application/json"))
              .andExpect(status().is(202))
              .andExpect(content().string(""));

          Thread.sleep(100);

          List<DetectionEntity> lastDetections =
              this.detectionRepository.findLastDetectionsById(
                  device.getId(), characteristic.getId(), 3);
          assert lastDetections.size() == 2;
          assert lastDetections.get(0).getValue() == 42d;
          assert lastDetections.get(1).getValue() == 70d;
          assert lastDetections.get(1).getOutlier();
        });
  }

  interface ThrowableRunnable {
    void run() throws Exception;
  }
}
