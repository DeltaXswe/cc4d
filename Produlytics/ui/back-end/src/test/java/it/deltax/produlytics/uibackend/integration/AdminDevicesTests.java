package it.deltax.produlytics.uibackend.integration;

import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.admins.devices.web.AdminsDevicesController;
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
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/** Test d'integrazione per le operazioni svolte dagli amministratori sulle macchine */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class AdminDevicesTests {
  @Autowired private AdminsDevicesController adminsDevicesController;

  @Autowired private DeviceRepository deviceRepository;

  @Autowired protected MockMvc mockMvc;

  private static int deviceId1;
  private static int deviceId2;

  @BeforeAll
  private static void prepareContext(@Autowired DeviceRepository deviceRepository) {
    DeviceEntity device1 = deviceRepository.save(new DeviceEntity("macchina1", false, false, "a1"));
    deviceId1 = device1.getId();

    DeviceEntity device2 = deviceRepository.save(new DeviceEntity("macchina2", false, false, "a2"));
    deviceId2 = device2.getId();
  }

  @AfterAll
  private static void deleteAll(
      @Autowired DeviceRepository deviceRepository,
      @Autowired CharacteristicRepository characteristicRepository) {
    characteristicRepository.deleteAll();
    deviceRepository.deleteAll();
  }

  @Test
  void contextLoads() {
    assertThat(this.adminsDevicesController).isNotNull();
    assertThat(this.deviceRepository).isNotNull();
  }

  /**
   * Testa l'inserimento di una nuova macchina
   *
   * @throws Exception
   */
  @Test
  public void testInsertDevice() throws Exception {
    JSONArray characteristics = new JSONArray();
    JSONObject characteristic1 =
        new JSONObject()
            .put("name", "pressione")
            .put("autoAdjust", "true")
            .put("sampleSize", 5)
            .put("archived", "false");
    characteristics.put(characteristic1);
    JSONObject device =
        new JSONObject().put("name", "macchina1000").put("characteristics", characteristics);

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/admin/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(device.toString())
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isOk());
  }

  /**
   * Testa l'inserimento di una nuova macchina
   *
   * @throws Exception esiste già una macchina con lo stesso nome
   */
  @Test
  public void testInsertDeviceDuplicateName() throws Exception {
    JSONArray characteristics = new JSONArray();
    JSONObject characteristic1 =
        new JSONObject()
            .put("name", "pressione")
            .put("autoAdjust", "true")
            .put("sampleSize", 5)
            .put("archived", "false");
    characteristics.put(characteristic1);
    JSONObject device =
        new JSONObject().put("name", "macchina1").put("characteristics", characteristics);

    this.mockMvc
        .perform(
            MockMvcRequestBuilders.post("/admin/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(device.toString())
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().json("{\"errorCode\":\"duplicateDeviceName\"}"));
  }

  /**
   * Testa l'ottenimento delle macchine memorizzate
   *
   * @throws Exception
   */
  @Test
  public void testGetDevices() throws Exception {
    deviceRepository.deleteAll();
    prepareContext(deviceRepository);

    JSONObject d1 =
        new JSONObject()
            .put("id", deviceId1)
            .put("name", "macchina1")
            .put("archived", false)
            .put("deactivated", false);
    JSONObject d2 =
        new JSONObject()
            .put("id", deviceId2)
            .put("name", "macchina2")
            .put("archived", false)
            .put("deactivated", false);

    String requestResponse = new JSONArray().put(d1).put(d2).toString();

    this.mockMvc
        .perform(
            get("/admin/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(requestResponse));
  }

  /**
   * Testa la modifica del nome di una macchina
   *
   * @throws Exception la macchina non è stata trovata
   */
  @Test
  public void testModifyDevice() throws Exception {
    this.mockMvc
        .perform(
            put("/admin/devices/" + deviceId1 + "/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content("macchina11")
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  /**
   * Testa la modifica del nome di una macchina non esistente
   *
   * @throws Exception la macchina non è stata trovata
   */
  @Test
  public void testModifyDeviceNotFound() throws Exception {
    JSONObject json = new JSONObject();
    json.put("name", "Macchina2");

    this.mockMvc
        .perform(
            put("/admin/devices/100/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(content().json("{\"errorCode\":\"deviceNotFound\"}"));
  }

  /**
   * Testa la modifica del nome di una macchina non esistente
   *
   * @throws Exception la macchina non è stata trovata
   */
  @Test
  public void testModifyDeviceDuplicateName() throws Exception {
    this.mockMvc
        .perform(
            put("/admin/devices/" + deviceId1 + "/name")
                .contentType(MediaType.APPLICATION_JSON)
                .content("macchina2")
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().json("{\"errorCode\":\"duplicateDeviceName\"}"));
  }

  /**
   * Testa la modifica dello stato di archiviazione di una macchina
   *
   * @throws Exception la macchina non è stata trovata
   */
  @Test
  public void testModifyDeviceArchiveStatus() throws Exception {
    this.mockMvc
        .perform(
            put("/admin/devices/" + deviceId1 + "/archived")
                .contentType(MediaType.APPLICATION_JSON)
                .content("true")
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  /**
   * Testa la modifica dello stato di archiviazione di una macchina che non esiste
   *
   * @throws Exception la macchina non è stata trovata
   */
  @Test
  public void testModifyDeviceArchiveStatusNotFound() throws Exception {
    this.mockMvc
        .perform(
            put("/admin/devices/2000/archived")
                .contentType(MediaType.APPLICATION_JSON)
                .content("true")
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(content().json("{\"errorCode\":\"deviceNotFound\"}"));
  }

  /**
   * Testa la modifica dello stato di attivazione di una macchina
   *
   * @throws Exception la macchina non è stata trovata
   */
  @Test
  public void testModifyDeviceDeactivateStatus() throws Exception {
    this.mockMvc
        .perform(
            put("/admin/devices/" + deviceId1 + "/deactivated")
                .contentType(MediaType.APPLICATION_JSON)
                .content("true")
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  /**
   * Testa la modifica dello stato di attivazione di una macchina che non esiste
   *
   * @throws Exception la macchina non è stata trovata
   */
  @Test
  public void testModifyDeviceDeactivateStatusNotFound() throws Exception {
    this.mockMvc
        .perform(
            put("/admin/devices/3000/deactivated")
                .contentType(MediaType.APPLICATION_JSON)
                .content("true")
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(content().json("{\"errorCode\":\"deviceNotFound\"}"));
  }

  /**
   * Testa l'ottenimento dei dettagli di una macchina
   *
   * @throws Exception la macchina non è stata trovata
   */
  @Test
  public void testGetDeviceDetailsOk() throws Exception {
    String d1 =
        new JSONObject()
            .put("id", deviceId1)
            .put("name", "macchina1")
            .put("archived", true)
            .put("deactivated", false)
            .toString();

    this.mockMvc
        .perform(get("/admin/devices/" + deviceId1))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(d1));
  }

  /**
   * Testa l'ottenimento dei dettagli di una macchina che non esiste
   *
   * @throws Exception la macchina non è stata trovata
   */
  @Test
  public void testGetDeviceDetailsNotFound() throws Exception {
    this.mockMvc
        .perform(get("/admin/devices/111"))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(content().json("{\"errorCode\":\"deviceNotFound\"}"));
    ;
  }
}
