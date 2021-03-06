package it.deltax.produlytics.uibackend.admins.devices.business.services;

import it.deltax.produlytics.uibackend.admins.devices.business.CharacteristicConstraints;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.CharacteristicConstraintsToCheck;
import it.deltax.produlytics.uibackend.admins.devices.business.domain.NewCharacteristic;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.in.InsertCharacteristicUseCase;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedCharacteristicPort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.FindDetailedDevicePort;
import it.deltax.produlytics.uibackend.admins.devices.business.ports.out.InsertCharacteristicPort;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;
import java.util.OptionalInt;

/** Il service per l'insierimento di una nuova caratteristica. */
public class InsertCharacteristicService implements InsertCharacteristicUseCase {
  private final InsertCharacteristicPort insertCharacteristicPort;
  private final FindDetailedDevicePort findDevicePort;
  private final FindDetailedCharacteristicPort findCharacteristicPort;

  /**
   * Il costruttore.
   *
   * @param insertCharacteristicPort la porta per inserire una nuova caratteristica in una macchina
   * @param findDevicePort la porta per trovare una macchina
   * @param findCharacteristicPort la porta per trovare una caratteristica
   */
  public InsertCharacteristicService(
      InsertCharacteristicPort insertCharacteristicPort,
      FindDetailedDevicePort findDevicePort,
      FindDetailedCharacteristicPort findCharacteristicPort) {
    this.insertCharacteristicPort = insertCharacteristicPort;
    this.findDevicePort = findDevicePort;
    this.findCharacteristicPort = findCharacteristicPort;
  }

  /**
   * Inserisce una nuova caratteristica in una macchina.
   *
   * @param deviceId l'id della macchina
   * @param characteristic la nuova caratteristica da inserire
   * @return l'id della nuova caratteristica
   * @throws BusinessException se la macchina non esiste, la caratteristica esiste gi?? oppure i
   *     valori inseriti non sono validi
   */
  public int insertByDevice(int deviceId, NewCharacteristic characteristic)
      throws BusinessException {
    this.findDevicePort
        .findDetailedDevice(deviceId)
        .orElseThrow(() -> new BusinessException("deviceNotFound", ErrorType.NOT_FOUND));

    if (this.findCharacteristicPort
        .findByDeviceAndName(deviceId, characteristic.name())
        .isPresent()) {
      throw new BusinessException("duplicateCharacteristicName", ErrorType.GENERIC);
    }

    if (characteristic.sampleSize().equals(OptionalInt.of(0))) {
      characteristic = characteristic.toBuilder().withSampleSize(OptionalInt.empty()).build();
    }

    if (!CharacteristicConstraints.characteristicConstraintsOk(
        CharacteristicConstraintsToCheck.builder()
            .withLowerLimit(characteristic.lowerLimit())
            .withUpperLimit(characteristic.upperLimit())
            .withAutoAdjust(characteristic.autoAdjust())
            .withSampleSize(characteristic.sampleSize())
            .build())) {
      throw new BusinessException("invalidValues", ErrorType.GENERIC);
    }

    return this.insertCharacteristicPort.insertByDevice(deviceId, characteristic);
  }
}
