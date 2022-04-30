package it.deltax.produlytics.persistence;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Questa classe rappresenta una rilevazione nella banca dati.
 */
@Entity
@Table(name = "detection")
@IdClass(DetectionEntityId.class)
public class DetectionEntity {
  /**
   * L'istante della rilevazione, rappresentato dai millisecondi trascorsi dallo UNIX epoch.
   */
  @Column(name = "creation_time", nullable = false)
  @Id
  private Instant creationTime;

  /**
   * L'identificativo della caratteristica all'interno della macchina a cui la rilevazione
   * appartiene.
   */
  @Column(name = "characteristic_id", nullable = false)
  @Id
  private Integer characteristicId;

  /**
   * L'identificativo della macchina a cui la caratteristica appartiene.
   */
  @Column(name = "device_id", nullable = false)
  @Id
  private Integer deviceId;

  /**
   * Il valore rilevato.
   */
  @Column(name = "value", nullable = false)
  private Double value;

  /**
   * `true` se la rilevazione è anomala; `false` altrimenti.
   */
  @Column(name = "outlier", nullable = false)
  private Boolean outlier;

  /**
   * Costruttore senza argomenti per JPA.
   */
  protected DetectionEntity() {}

  /**
   * Crea una nuova istanza di `DetectionEntity`.
   *
   * @param creationTime Il valore per il campo `creationTime`.
   * @param characteristicId Il valore per il campo `characteristicId`.
   * @param deviceId Il valore per il campo `deviceId`.
   * @param value Il valore per il campo `value`.
   * @param outlier Il valore per il campo `outlier`.
   */
  public DetectionEntity(
      Instant creationTime,
      Integer characteristicId,
      Integer deviceId,
      Double value,
      Boolean outlier) {
    this.creationTime = creationTime;
    this.characteristicId = characteristicId;
    this.deviceId = deviceId;
    this.value = value;
    this.outlier = outlier;
  }

  /**
   * Getter per il campo `creationTime`.
   *
   * @return Il valore del campo `creationTime`.
   */
  public Instant getCreationTime() {
    return creationTime;
  }

  /**
   * Getter per il campo `characteristicId`.
   *
   * @return Il valore del campo `characteristicId`.
   */
  public Integer getCharacteristicId() {
    return characteristicId;
  }

  /**
   * Getter per il campo `deviceId`.
   *
   * @return Il valore del campo `deviceId`.
   */
  public Integer getDeviceId() {
    return deviceId;
  }

  /**
   * Getter per il campo `value`.
   *
   * @return Il valore del campo `value`.
   */
  public Double getValue() {
    return this.value;
  }

  /**
   * Getter per il campo `outlier`.
   *
   * @return Il valore del campo `outlier`.
   */
  public Boolean getOutlier() {
    return this.outlier;
  }
}
