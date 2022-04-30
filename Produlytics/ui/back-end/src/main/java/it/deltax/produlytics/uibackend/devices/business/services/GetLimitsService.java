package it.deltax.produlytics.uibackend.devices.business.services;

import it.deltax.produlytics.uibackend.devices.business.domain.CharacteristicLimits;
import it.deltax.produlytics.uibackend.devices.business.ports.in.GetLimitsUseCase;
import it.deltax.produlytics.uibackend.devices.business.ports.out.FindCharacteristicLimitsPort;
import it.deltax.produlytics.uibackend.devices.business.ports.out.GetUnarchivedDevicesPort;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.exceptions.ErrorType;

/** Il service per l'ottenimento dei limiti tecnici di una caratteristica non archiviata */
public class GetLimitsService implements GetLimitsUseCase {
  private final GetUnarchivedDevicesPort findDevicesPort;
  private final FindCharacteristicLimitsPort findCharacteristicLimitsPort;

  /**
   * Il costruttore
   *
   * @param findDevicesPort la porta per ottenere le macchine non archiviate
   * @param findCharacteristicLimitsPort la porta per ottenere i limiti di una caratteristica
   */
  public GetLimitsService(
      GetUnarchivedDevicesPort findDevicesPort,
      FindCharacteristicLimitsPort findCharacteristicLimitsPort) {
    this.findDevicesPort = findDevicesPort;
    this.findCharacteristicLimitsPort = findCharacteristicLimitsPort;
  }

  /**
   * Restituisce i limiti tecnici di un caratteristica non archiviata
   *
   * @param deviceId l'id della macchina
   * @param characteristicId l'id della caratteristica
   * @return i limiti tecnici della caratteristica
   * @throws BusinessException se la caratteristica è inesistente oppure è archiviata
   */
  @Override
  public CharacteristicLimits getByCharacteristic(int deviceId, int characteristicId)
      throws BusinessException {
    if (findDevicesPort.getUnarchivedDevices().stream()
        .filter(device -> device.id() == deviceId)
        .toList()
        .isEmpty()) {
      throw new BusinessException("characteristicNotFound", ErrorType.NOT_FOUND);
    }

    return this.findCharacteristicLimitsPort
        .findByCharacteristic(deviceId, characteristicId)
        .orElseThrow(() -> new BusinessException("characteristicNotFound", ErrorType.NOT_FOUND));
  }
}
