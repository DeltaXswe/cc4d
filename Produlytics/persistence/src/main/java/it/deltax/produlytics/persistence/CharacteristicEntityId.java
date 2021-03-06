package it.deltax.produlytics.persistence;

import java.io.Serializable;
import java.util.Objects;

/**
 * Questa classe rappresenta l'identificativo di una caratteristica salvata nella banca dati.
 */
public class CharacteristicEntityId implements Serializable {
  /**
   * L'identificativo della caratteristica all'interno della macchina.
   */
  private Integer id;

  /**
   * L'identificativo della macchina a cui la caratteristica appartiene.
   */
  private Integer deviceId;

  /**
   * Costruttore senza argomenti per JPA.
   */
  protected CharacteristicEntityId() {}

  /**
   * Crea una nuova istanza di {@code CharacteristicEntityId}.
   *
   * @param id il valore per il campo {@code id}
   * @param deviceId il valore per il campo {@code deviceId}
   */
  public CharacteristicEntityId(Integer deviceId, Integer id) {
    this.deviceId = deviceId;
    this.id = id;
  }

  /**
   * Questo costruttore crea la chiave di una caratteristica senza il campo {@code id}.
   *
   * @param deviceId il valore per il campo {@code deviceId}
   */
  public CharacteristicEntityId(Integer deviceId) {
    this.deviceId = deviceId;
  }

  /**
   * Getter per il campo {@code id}.
   *
   * @return il valore del campo {@code id}
   */
  public Integer getId() {
    return this.id;
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
    CharacteristicEntityId that = (CharacteristicEntityId) o;
    return getId().equals(that.getId()) && getDeviceId().equals(that.getDeviceId());
  }

  /**
   * Calcola l'hash di questo oggetto.
   *
   * @return l'hash di questo oggetto
   */
  @Override
  public int hashCode() {
    return Objects.hash(getId(), getDeviceId());
  }
}
