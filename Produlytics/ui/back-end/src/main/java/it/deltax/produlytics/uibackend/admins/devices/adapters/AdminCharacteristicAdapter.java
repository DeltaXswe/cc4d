package it.deltax.produlytics.uibackend.admins.devices.adapters;

import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.Characteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindAllCharacteristicsPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.InsertCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.UpdateCharacteristicPort;
import it.deltax.produlytics.uibackend.repositories.CharacteristicRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;

/**
 * L'adapter dello strato di persistenza per le operazioni svolte dagli amministratori sulle
 * caratteristiche
 */
@Component
public class AdminCharacteristicAdapter
    implements FindDetailedCharacteristicPort,
        InsertCharacteristicPort,
        FindAllCharacteristicsPort,
        UpdateCharacteristicPort {
  private final CharacteristicRepository repo;

  /**
   * Il costruttore
   *
   * @param repo lo strato di persistenza con i dati sulle caratteristiche
   */
  public AdminCharacteristicAdapter(CharacteristicRepository repo) {
    this.repo = repo;
  }

  /**
   * Restituisce tutte le caratteristiche di una macchina
   *
   * @param deviceId l'id della macchina
   * @return la lista delle caratteristiche della macchina
   */
  @Override
  public List<Characteristic> findAllByDeviceId(int deviceId) {
    return repo.findByDeviceId(deviceId).stream()
        .map(
            characteristic ->
                Characteristic.builder()
                    .withId(characteristic.getId())
                    .withName(characteristic.getName())
                    .withArchived(characteristic.getArchived())
                    .build())
        .toList();
  }

  /**
   * Restituisce tutti i dettagli della caratteristica di una macchina
   *
   * @param deviceId l'id della macchina
   * @param characteristicId l'id della caratteristica
   * @return la caratteristica trovata
   */
  @Override
  public Optional<DetailedCharacteristic> findByCharacteristic(int deviceId, int characteristicId) {
    return this.repo
        .findById(new CharacteristicEntityId(deviceId, characteristicId))
        .map(ConvertCharacteristic::toDetailed);
  }

  /**
   * Restituisce le caratteristiche di una macchina con un dato nome
   *
   * @param deviceId l'id della macchina
   * @param name il nome delle caratteristiche
   * @return la lista delle caratteristiche trovate
   */
  @Override
  public List<DetailedCharacteristic> findByDeviceAndName(int deviceId, String name) {
    return this.repo.findByDeviceIdAndName(deviceId, name).stream()
        .map(ConvertCharacteristic::toDetailed)
        .toList();
  }

  /**
   * Inserisce una nuova caratteristica in una macchina
   *
   * @param deviceId l'id della macchina
   * @param characteristic la caratteristica da inserire
   * @return l'id della nuova caratteristica
   */
  @Override
  public int insertByDevice(int deviceId, NewCharacteristic characteristic) {
    this.repo.save(
        new CharacteristicEntity(
            deviceId,
            characteristic.name(),
            characteristic.upperLimit().isPresent()
                ? characteristic.upperLimit().getAsDouble()
                : null,
            characteristic.lowerLimit().isPresent()
                ? characteristic.lowerLimit().getAsDouble()
                : null,
            characteristic.autoAdjust(),
            characteristic.sampleSize().isPresent() ? characteristic.sampleSize().getAsInt() : null,
            false));
    return this.repo.findByDeviceIdAndName(deviceId, characteristic.name()).get().getId();
  }

  /**
   * Modifica le informazioni di una caratteristica
   *
   * @param characteristic la caratteristica da modificare
   */
  @Override
  public void updateCharacteristic(DetailedCharacteristic characteristic) {
    this.repo.save(ConvertCharacteristic.toEntity(characteristic));
  }
}
