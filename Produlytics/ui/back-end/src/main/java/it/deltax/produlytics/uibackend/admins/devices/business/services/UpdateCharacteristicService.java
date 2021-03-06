package it.deltax.produlytics.uibackend.admins.devices.business.services;

import it.deltax.produlytics.uibackend.admins.devices.business.CharacteristicConstraints;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.CharacteristicConstraintsToCheck;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.CharacteristicToUpdate;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.DetailedCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.UpdateCharacteristicUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.UpdateCharacteristicPort;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import java.util.Optional;
import java.util.OptionalInt;

/** Il service per la modifica di una caratteristica. */
public class UpdateCharacteristicService implements UpdateCharacteristicUseCase {
  private final FindDetailedCharacteristicPort findCharacteristicPort;
  private final UpdateCharacteristicPort updateCharacteristicPort;

  /**
   * Il costruttore.
   *
   * @param findCharacteristicPort la porta per trovare una caratteristica completa di tutte le sue
   *     informazioni
   * @param updateCharacteristicPort la porta per aggiornare le informazioni della caratteristica
   */
  public UpdateCharacteristicService(
      FindDetailedCharacteristicPort findCharacteristicPort,
      UpdateCharacteristicPort updateCharacteristicPort) {
    this.findCharacteristicPort = findCharacteristicPort;
    this.updateCharacteristicPort = updateCharacteristicPort;
  }

  /**
   * Modifica le informazioni di una caratteristica.
   *
   * @param toUpdate le informazioni della caratteristica da aggiornare
   * @throws BusinessException se le informazioni non sono valide o la caratteristica è inesistente
   */
  @Override
  public void updateCharacteristic(CharacteristicToUpdate toUpdate) throws BusinessException {
    final boolean archived =
        this.findCharacteristicPort
            .findByCharacteristic(toUpdate.deviceId(), toUpdate.id())
            .orElseThrow(() -> new BusinessException("characteristicNotFound", ErrorType.NOT_FOUND))
            .archived();

    // Trovo gli stesso-nome-stessa-macchina
    Optional<DetailedCharacteristic> omonimo =
        this.findCharacteristicPort.findByDeviceAndName(toUpdate.deviceId(), toUpdate.name());
    // beh, se ha stesso nome, stessa macchina, e stesso id allora è se stesso e
    // si sta facendo l'update senza modificare il nome
    // altrimenti, è un nome duplicato
    if (omonimo.isPresent() && omonimo.get().id() != toUpdate.id()) {
      throw new BusinessException("duplicateCharacteristicName", ErrorType.GENERIC);
    }

    if (toUpdate.sampleSize().equals(OptionalInt.of(0))) {
      toUpdate = toUpdate.toBuilder().withSampleSize(OptionalInt.empty()).build();
    }

    if (!CharacteristicConstraints.characteristicConstraintsOk(
        CharacteristicConstraintsToCheck.builder()
            .withLowerLimit(toUpdate.lowerLimit())
            .withUpperLimit(toUpdate.upperLimit())
            .withAutoAdjust(toUpdate.autoAdjust())
            .withSampleSize(toUpdate.sampleSize())
            .build())) {
      throw new BusinessException("invalidValues", ErrorType.GENERIC);
    }

    this.updateCharacteristicPort.updateCharacteristic(
        DetailedCharacteristic.builder()
            .withId(toUpdate.id())
            .withDeviceId(toUpdate.deviceId())
            .withName(toUpdate.name())
            .withLowerLimit(toUpdate.lowerLimit())
            .withUpperLimit(toUpdate.upperLimit())
            .withAutoAdjust(toUpdate.autoAdjust())
            .withSampleSize(toUpdate.sampleSize())
            .withArchived(archived)
            .build());
  }
}
