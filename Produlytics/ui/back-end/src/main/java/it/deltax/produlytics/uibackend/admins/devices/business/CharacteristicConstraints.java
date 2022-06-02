package it.deltax.produlytics.uibackend.admins.devices.business;

import it.deltax.produlytics.uibackend.admins.devices.business.domain.CharacteristicConstraintsToCheck;

/** Interfaccia che verifica il rispetto dei vincoli di una caratteristica. */
public interface CharacteristicConstraints {
  /**
   * Verifica che le informazioni di una caratteristica rispettino i vincoli.
   *
   * @param toCheck le informazioni su cui vanno verificati i vincoli
   * @return true se e solo se le informazioni rispettano i vincoli
   */
  static boolean characteristicConstraintsOk(CharacteristicConstraintsToCheck toCheck) {
    final boolean autoAdjust = toCheck.autoAdjust();
    final boolean technicalLimits =
        toCheck.upperLimit().isPresent() && toCheck.lowerLimit().isPresent();

    return autoAdjust || technicalLimits;
  }
}
