package it.deltax.produlytics.uibackend.admins.devices.web;

import com.fasterxml.jackson.databind.JsonNode;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.Device;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceArchiveStatus;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceDeactivateStatus;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DeviceToInsert;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.GetDeviceDetailsUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.GetDevicesUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.InsertDeviceUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.UpdateDeviceArchiveStatusUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.UpdateDeviceDeactivateStatusUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.UpdateDeviceNameUseCase;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import java.util.HashMap;
import java.util.Map;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Il controller per le richieste effettuate dagli amministratori riguradanti le macchine. */
@RestController
@RequestMapping("/admin")
public class AdminsDevicesController {
  private final InsertDeviceUseCase insertDeviceUseCase;
  private final GetDevicesUseCase getDevicesUseCase;
  private final GetDeviceDetailsUseCase getDeviceDetailsUseCase;
  private final UpdateDeviceNameUseCase updateDeviceNameUseCase;
  private final UpdateDeviceArchiveStatusUseCase updateDeviceArchiveStatusUseCase;
  private final UpdateDeviceDeactivateStatusUseCase updateDeviceDeactivateStatusUseCase;

  /**
   * Il costruttore.
   *
   * @param insertDeviceUseCase l'interfaccia per il caso d'uso d'inserimento di una macchina
   * @param getDevicesUseCase l'interfaccia per il caso d'uso di ottenimento delle macchine
   * @param getDeviceDetailsUseCase l'interfaccia per il caso d'uso di ottenimento delle macchine
   *     dettagliate
   * @param updateDeviceNameUseCase l'interfaccia per il caso d'uso di aggiornamento del nome di una
   *     macchina
   * @param updateDeviceArchiveStatusUseCase l'interfaccia per il caso d'uso di aggiornamento dello
   *     stato di archiviazione di una macchina
   * @param updateDeviceDeactivateStatusUseCase l'interfaccia per il caso d'uso di aggiornamento
   *     dello stato di attivazione di una macchina
   */
  public AdminsDevicesController(
      InsertDeviceUseCase insertDeviceUseCase,
      GetDevicesUseCase getDevicesUseCase,
      GetDeviceDetailsUseCase getDeviceDetailsUseCase,
      UpdateDeviceNameUseCase updateDeviceNameUseCase,
      UpdateDeviceArchiveStatusUseCase updateDeviceArchiveStatusUseCase,
      UpdateDeviceDeactivateStatusUseCase updateDeviceDeactivateStatusUseCase) {
    this.insertDeviceUseCase = insertDeviceUseCase;
    this.getDevicesUseCase = getDevicesUseCase;
    this.getDeviceDetailsUseCase = getDeviceDetailsUseCase;
    this.updateDeviceNameUseCase = updateDeviceNameUseCase;
    this.updateDeviceArchiveStatusUseCase = updateDeviceArchiveStatusUseCase;
    this.updateDeviceDeactivateStatusUseCase = updateDeviceDeactivateStatusUseCase;
  }

  /**
   * Riceve le chiamate all'endpoint REST per l'inserimento di una macchina.
   *
   * @param device la macchina da inserire con le sue caratteristiche
   * @return lo stato HTTP
   * @throws BusinessException se una caratteristica non è valida o è un duplicato
   */
  @PostMapping("/devices")
  public ResponseEntity<Map<String, String>> insertDevice(@RequestBody DeviceToInsert device)
      throws BusinessException {
    int id = this.insertDeviceUseCase.insertDevice(device);
    Map<String, String> map = new HashMap<>();
    map.put("id", String.valueOf(id));
    return ResponseEntity.ok(map);
  }

  /**
   * Riceve le chiamate all'endpoint REST per l'ottenimento delle macchine.
   *
   * @return lo stato HTTP e la lista delle macchine
   */
  @GetMapping("/devices")
  public ResponseEntity<Iterable<Device>> getDevices() {
    return ResponseEntity.ok(this.getDevicesUseCase.getDevices());
  }

  /**
   * Riceve le chiamate all'endpoint REST per l'ottenimento dei dettagli di una macchina.
   *
   * @param id l'id della macchina da ottenere
   * @return la macchina dettagliata
   * @throws BusinessException se la macchina non è stata trovata
   */
  @GetMapping("/devices/{id}")
  public ResponseEntity<DetailedDevice> getDeviceDetails(@PathVariable("id") int id)
      throws BusinessException {
    return ResponseEntity.ok(this.getDeviceDetailsUseCase.getDeviceDetails(id));
  }

  /**
   * Riceve le chiamate all'endpoint REST per l'aggiornamento del nome di una macchina.
   *
   * @param id l'id della macchina da aggiornare
   * @param body il corpo della richiesta HTTP
   * @return lo stato HTTP
   * @throws BusinessException se la macchina non è stata trovata
   */
  @PutMapping("/devices/{id}/name")
  public ResponseEntity<String> updateDeviceName(
      @PathVariable("id") int id, @RequestBody JsonNode body) throws BusinessException {
    String name = body.get("name").asText();
    System.out.println(name);
    this.updateDeviceNameUseCase.updateDeviceName(new TinyDevice(id, name));
    return new ResponseEntity<>(NO_CONTENT);
  }

  /**
   * Riceve le chiamate all'endpoint REST per l'aggiornamento dello stato di archiviazione di una
   * macchina.
   *
   * @param id l'id della macchina da aggiornare
   * @param body il corpo della richiesta HTTP
   * @return lo stato HTTP
   * @throws BusinessException se la macchina non è stata trovata
   */
  @PutMapping("devices/{id}/archived")
  public ResponseEntity<String> updateDeviceArchiveStatus(
      @PathVariable("id") int id, @RequestBody JsonNode body) throws BusinessException {
    boolean archived = body.get("archived").asBoolean();
    this.updateDeviceArchiveStatusUseCase.updateDeviceArchiveStatus(
        new DeviceArchiveStatus(id, archived));
    return new ResponseEntity<>(NO_CONTENT);
  }

  /**
   * Riceve le chiamate all'endpoint REST per l'aggiornamento dello stato di attivazione di una
   * macchina.
   *
   * @param id l'id della macchina da aggiornare
   * @param body il corpo della richiesta HTTP
   * @return lo stato HTTP
   * @throws BusinessException se la macchina non è stata trovata
   */
  @PutMapping("/devices/{id}/deactivated")
  public ResponseEntity<String> updateDeviceDeactivateStatus(
      @PathVariable("id") int id, @RequestBody JsonNode body) throws BusinessException {
    boolean deactivated = body.get("deactivated").asBoolean();
    this.updateDeviceDeactivateStatusUseCase.updateDeviceDeactivateStatus(
        new DeviceDeactivateStatus(id, deactivated));
    return new ResponseEntity<>(NO_CONTENT);
  }
}
