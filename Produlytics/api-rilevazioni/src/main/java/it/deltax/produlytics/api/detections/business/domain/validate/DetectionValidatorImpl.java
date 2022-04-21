package it.deltax.produlytics.api.detections.business.domain.validate;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.detections.business.ports.out.FindCharacteristicByNamePort;
import it.deltax.produlytics.api.detections.business.ports.out.FindDeviceByApiKeyPort;
import it.deltax.produlytics.api.exceptions.BusinessException;
import it.deltax.produlytics.api.exceptions.ErrorType;
import lombok.AllArgsConstructor;

/** Questa classe si occupa di validare le rilevazioni in arrivo da una macchina. */
@AllArgsConstructor
public class DetectionValidatorImpl implements DetectionValidator {
  /**
   * Una porta necessaria per ottenere le informazioni di una macchina a partire dalla sua chiave
   * API.
   */
  private final FindDeviceByApiKeyPort findDeviceByApiKeyPort;

  /**
   * Una porta necessaria per ottenere le informazioni di una caratteristica a partire
   * dall'identificativo della macchina a cui appartiene e il suo nome.
   */
  private final FindCharacteristicByNamePort findCharacteristicByNamePort;

  /**
   * Questo metodo implementa l'omonimo metodo definito in `DetectionValidator`.
   *
   * @param apiKey La chiave API fornita dalla macchina e da validare.
   * @param characteristicName Il nome della caratteristica, all'interno della macchina, da
   *     validare.
   * @return L'identificativo globale della caratteristica corrispondente ai parametri ricevuti.
   * @throws BusinessException Se la macchina o caratteristica non esistono, o se sono archiviate o
   *     disattivate.
   */
  @Override
  public CharacteristicId validateAndFindId(String apiKey, String characteristicName)
      throws BusinessException {

    DeviceInfo deviceInfo =
        this.findDeviceByApiKeyPort
            .findDeviceByApiKey(apiKey)
            .orElseThrow(() -> new BusinessException("notAuthenticated", ErrorType.AUTHENTICATION));

    if (deviceInfo.archived() || deviceInfo.deactivated()) {
      throw new BusinessException("archived", ErrorType.ARCHIVED);
    }

    CharacteristicInfo characteristicInfo =
        this.findCharacteristicByNamePort
            .findCharacteristicByName(deviceInfo.deviceId(), characteristicName)
            .orElseThrow(
                () -> new BusinessException("characteristicNotFound", ErrorType.NOT_FOUND));

    if (characteristicInfo.archived()) {
      throw new BusinessException("archived", ErrorType.ARCHIVED);
    }

    return new CharacteristicId(deviceInfo.deviceId(), characteristicInfo.characteristicId());
  }
}
