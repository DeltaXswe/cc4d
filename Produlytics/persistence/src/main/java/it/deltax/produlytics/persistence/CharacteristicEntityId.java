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
   * Crea una nuova istanza di `CharacteristicEntityId`.
   *
   * @param id Il valore per il campo `id`.
   * @param deviceId Il valore per il campo `deviceId`.
   */
  public CharacteristicEntityId(Integer deviceId, Integer id) {
    this.deviceId = deviceId;
    this.id = id;
  }

  /**
   * Questo costruttore crea la chiave di una caratteristica senza il campo `id`.
   *
   * @param deviceId Il valore per il campo `deviceId`.
   */
  public CharacteristicEntityId(Integer deviceId) {
    this.deviceId = deviceId;
  }

  /**
   * Getter per il campo `id`.
   *
   * @return Il valore del campo `id`.
   */
  public Integer getId() {
    return this.id;
  }

  /**
   * Getter per il campo `deviceId`.
   *
   * @return Il valore del campo `deviceId`.
   */
  public Integer getDeviceId() {
    return this.deviceId;
  }

  /**
   * Compara questo oggetto con un altro oggetto, e restituisce `true` se sono uguali.
   *
   * @param o L'oggetto con cui comparare questo oggetto.
   * @return `true` se sono uguali; `false` altrimenti.
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
   * @return L'hash di questo oggetto.
   */
  @Override
  public int hashCode() {
    return Objects.hash(getId(), getDeviceId());
  }
}
