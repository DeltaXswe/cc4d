package it.deltax.produlytics.uibackend.admins.devices.adapters;

import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.Device;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewDevice;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedDevicePort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindTinyDeviceByNamePort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindTinyDevicePort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.GetDevicesPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.InsertDevicePort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.UpdateDeviceArchiveStatusPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.UpdateDeviceDeactivateStatusPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.UpdateDeviceNamePort;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * L'adapter dello strato di persistenza per le operazioni svolte dagli amministratori sulle
 * macchine.
 */
@Component
public class AdminDeviceAdapter
    implements InsertDevicePort,
        GetDevicesPort,
        FindTinyDevicePort,
        FindTinyDeviceByNamePort,
        FindDetailedDevicePort,
        UpdateDeviceArchiveStatusPort,
        UpdateDeviceDeactivateStatusPort,
        UpdateDeviceNamePort {
  private final DeviceRepository repo;

  /**
   * Il costruttore.
   *
   * @param repo lo strato di persistenza con i dati sulle macchine
   */
  public AdminDeviceAdapter(DeviceRepository repo) {
    this.repo = repo;
  }

  /**
   * Restituisce la lista delle macchine memorizzate nello strato di persistenza.
   *
   * @return la lista delle macchine
   */
  @Override
  public List<Device> getDevices() {
    return this.repo.findAllByOrderById().stream()
        .map(
            device ->
                new Device(
                    device.getId(),
                    device.getName(),
                    device.getArchived(),
                    device.getDeactivated()))
        .toList();
  }

  /**
   * Trova nello strato di persistenza una macchina, dato il suo id.
   *
   * @param deviceId l'id della macchina da trovare
   * @return la macchina, se trovata, con id e nome; Optional vuoto, altrimenti
   */
  @Override
  public Optional<TinyDevice> findTinyDevice(int deviceId) {
    return this.repo
        .findById(deviceId)
        .map(device -> new TinyDevice(device.getId(), device.getName()));
  }

  /**
   * Trova nello strato di persistenza una macchina, dato il suo nome.
   *
   * @param name il nome della macchina da trovare
   * @return la macchina, se trovata, con id e nome; Optional vuoto, altrimenti
   */
  @Override
  public Optional<TinyDevice> findByName(String name) {
    return this.repo.findByName(name).stream()
        .findFirst()
        .map(device -> new TinyDevice(device.id(), device.name()));
  }

  /**
   * Trova nello strato di persistenza una macchina, dato il suo id.
   *
   * @param deviceId l'id della macchina da trovare
   * @return la macchina, se trovata, con id, nome, stato di archiviazione, stato di attivazione e
   *     chiave dell'API; Optional vuoto, altrimenti
   */
  @Override
  public Optional<DetailedDevice> findDetailedDevice(int deviceId) {
    return this.repo
        .findById(deviceId)
        .map(
            device ->
                new DetailedDevice(
                    device.getId(),
                    device.getName(),
                    device.getArchived(),
                    device.getDeactivated(),
                    device.getApikey()));
  }

  /**
   * Aggiorna lo stato di archiviazione della macchina data nello strato di persistenza.
   *
   * @param device la macchina con lo stato di archiviazione aggiornato da memorizzare
   */
  @Override
  public void updateDeviceArchiveStatus(DetailedDevice device) {
    DeviceEntity update =
        new DeviceEntity(
            device.id(), device.name(), device.archived(), device.deactivated(), device.apiKey());
    this.repo.save(update);
  }

  /**
   * Aggiorna lo stato di attivazione della macchina data nello strato di persistenza.
   *
   * @param device la macchina con lo stato di attivazione aggiornato da memorizzare
   */
  @Override
  public void updateDeviceDeactivateStatus(DetailedDevice device) {
    DeviceEntity update =
        new DeviceEntity(
            device.id(), device.name(), device.archived(), device.deactivated(), device.apiKey());
    this.repo.save(update);
  }

  /**
   * Aggiorna il nome della macchina data nello strato di persistenza.
   *
   * @param device la macchina con il nome aggiornato da memorizzare
   */
  @Override
  public void updateDeviceName(DetailedDevice device) {
    this.repo.save(
        new DeviceEntity(device.id(), device.name(), device.archived(), device.deactivated(), device.apiKey()));
  }

  /**
   * Inserisce una macchina nello strato di persistenza.
   *
   * @param device la macchina da memorizzare
   */
  @Override
  public int insertDevice(NewDevice device) {
    DeviceEntity entity =
        this.repo.saveAndFlush(
            new DeviceEntity(
                device.name(), device.archived(), device.deactivated(), device.apiKey()));
    return entity.getId();
  }
}
