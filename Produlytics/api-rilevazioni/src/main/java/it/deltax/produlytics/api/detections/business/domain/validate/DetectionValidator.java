package it.deltax.produlytics.api.detections.business.domain.validate;

import it.deltax.produlytics.api.detections.business.domain.CharacteristicId;
import it.deltax.produlytics.api.exceptions.BusinessException;

/** Questa interfaccia descrive l'abilità di validare una rilevazione in arrivo da una macchina. */
public interface DetectionValidator {
  /**
   * Questo metodo si occupa di validare una rilevazione in arrivo, in particolare valida la sua
   * chiave API e il nome della caratteristica, controllando che esistano e che non siano
   * archiviate/disattivate. In caso di successo ritorna l'identificativo globale della
   * caratteristica, altrimenti lancia un'eccezione con la motivazione del fallimento.
   *
   * @param apiKey La chiave API fornita dalla macchina e da validare.
   * @param characteristicName Il nome della caratteristica, all'interno della macchina, da
   *     validare.
   * @return L'identificativo globale della caratteristica corrispondente ai parametri ricevuti.
   * @throws BusinessException Se la macchina o caratteristica non esistono, o se sono archiviate o
   *     disattivate.
   */
  CharacteristicId validateAndFindId(String apiKey, String characteristicName)
      throws BusinessException;
}
