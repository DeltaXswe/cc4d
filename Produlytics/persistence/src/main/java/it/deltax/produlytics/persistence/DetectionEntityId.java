package it.deltax.produlytics.persistence;

import java.io.Serializable;
import java.time.Instant;
import java.util.Objects;

/**
 * Questa classe rappresenta l'identificativo di una rilevazione salvata nella banca dati.
 */
public class DetectionEntityId implements Serializable {
  /**
   * L'istante della rilevazione, rappresentato dai millisecondi trascorsi dallo UNIX epoch.
   */
  private Instant creationTime;

  /**
   * L'identificativo della caratteristica all'interno della macchina a cui la rilevazione
   * appartiene.
   */
  private Integer characteristicId;

  /**
   * L'identificativo della macchina a cui la caratteristica appartiene.
   */
  private Integer deviceId;

  /**
   * Costruttore senza argomenti per JPA.
   */
  protected DetectionEntityId() {}

  /**
   * Crea una nuova istanza di {@code DetectionEntityId}.
   *
   * @param creationTime il valore per il campo {@code creationTime}
   * @param characteristicId il valore per il campo {@code characteristicId}
   * @param deviceId il valore per il campo {@code deviceId}
   */
  public DetectionEntityId(Instant creationTime, Integer characteristicId, Integer deviceId) {
    this.creationTime = creationTime;
    this.characteristicId = characteristicId;
    this.deviceId = deviceId;
  }

  /**
   * Getter per il campo {@code creationTime}.
   *
   * @return il valore del campo {@code creationTime}
   */
  public Instant getCreationTime() {
    return creationTime;
  }

  /**
   * Getter per il campo {@code characteristicId}.
   *
   * @return il valore del campo {@code characteristicId}
   */
  public Integer getCharacteristicId() {
    return this.characteristicId;
  }

  /**
   * Getter per il campo {@code deviceId}.
   *
   * @return il valore del campo {@code deviceId}
   */
  public Integer getDeviceId() {
    return this.deviceId;
  }

  /**
   * Compara questo oggetto con un altro oggetto, e restituisce {@code true} se sono uguali.
   *
   * @param o l'oggetto con cui comparare questo oggetto
   * @return {@code true} se sono uguali; {@code false} altrimenti
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DetectionEntityId that = (DetectionEntityId) o;
    return getCreationTime().equals(that.getCreationTime())
        && getCharacteristicId().equals(that.getCharacteristicId())
        && getDeviceId().equals(that.getDeviceId());
  }

  /**
   * Calcola l'hash di questo oggetto.
   *
   * @return l'hash di questo oggetto
   */
  @Override
  public int hashCode() {
    return Objects.hash(getCreationTime(), getCharacteristicId(), getDeviceId());
  }
}
