package it.deltax.produlytics.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

/**
 * Questa classe rappresenta una caratteristica salvata nella banca dati.
 */
@Entity
@Table(name = "characteristic")
@IdClass(CharacteristicEntityId.class)
public class CharacteristicEntity {
  /**
   * L'identificativo della caratteristica all'interno della macchina.
   */
  @GeneratedValue(strategy = GenerationType.TABLE)
  @Column(name = "id", nullable = false)
  @Id
  private Integer id;

  /**
   * L'identificativo della macchina a cui la caratteristica appartiene.
   */
  @Column(name = "device_id", nullable = false)
  @Id
  private Integer deviceId;

  /**
   * Il nome della caratteristica.
   */
  @Column(name = "name", nullable = false)
  private String name;

  /**
   * Il limite tecnico superiore.
   */
  @Column(name = "upper_limit", nullable = true)
  private Double upperLimit;

  /**
   * Il limite tecnico inferiore.
   */
  @Column(name = "lower_limit", nullable = true)
  private Double lowerLimit;

  /**
   * `true` se l'autoadjust è attivo; `false` altrimenti.
   */
  @Column(name = "auto_adjust", nullable = false)
  private Boolean autoAdjust;

  /**
   * La grandezza del campione necessario a calcolare la media e la varianza.
   */
  @Column(name = "sample_size", nullable = true)
  private Integer sampleSize;

  /**
   * Lo stato di archiviazione della caratteristica.
   */
  @Column(name = "archived", nullable = false)
  private Boolean archived;

  /**
   * Costruttore senza argomenti per JPA.
   */
  protected CharacteristicEntity() {}

  /**
   * Crea una nuova istanza di `CharacteristicEntity`.
   *
   * @param id Il valore per il campo `id`.
   * @param deviceId Il valore per il campo `deviceId`.
   * @param name Il valore per il campo `name`.
   * @param upperLimit Il valore per il campo `upperLimit`.
   * @param lowerLimit Il valore per il campo `lowerLimit`.
   * @param autoAdjust Il valore per il campo `autoAdjust`.
   * @param sampleSize Il valore per il campo `sampleSize`.
   * @param archived Il valore per il campo `archived`.
   */
  public CharacteristicEntity(
      Integer id,
      Integer deviceId,
      String name,
      Double upperLimit,
      Double lowerLimit,
      Boolean autoAdjust,
      Integer sampleSize,
      Boolean archived) {
    this.id = id;
    this.deviceId = deviceId;
    this.name = name;
    this.upperLimit = upperLimit;
    this.lowerLimit = lowerLimit;
    this.autoAdjust = autoAdjust;
    this.sampleSize = sampleSize;
    this.archived = archived;
  }

  /**
   * Questo costruttore crea una caratteristica senza il campo `id`.
   *
   * @param deviceId Il valore per il campo `deviceId`.
   * @param name Il valore per il campo `name`.
   * @param upperLimit Il valore per il campo `upperLimit`.
   * @param lowerLimit Il valore per il campo `lowerLimit`.
   * @param autoAdjust Il valore per il campo `autoAdjust`.
   * @param sampleSize Il valore per il campo `sampleSize`.
   * @param archived Il valore per il campo `archived`.
   */
  public CharacteristicEntity(
      Integer deviceId,
      String name,
      Double upperLimit,
      Double lowerLimit,
      Boolean autoAdjust,
      Integer sampleSize,
      Boolean archived) {
    this.deviceId = deviceId;
    this.name = name;
    this.upperLimit = upperLimit;
    this.lowerLimit = lowerLimit;
    this.autoAdjust = autoAdjust;
    this.sampleSize = sampleSize;
    this.archived = archived;
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
   * Getter per il campo `name`.
   *
   * @return Il valore del campo `name`.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Getter per il campo `upperLimit`.
   *
   * @return Il valore del campo `upperLimit`.
   */
  public Double getUpperLimit() {
    return this.upperLimit;
  }

  /**
   * Getter per il campo `lowerLimit`.
   *
   * @return Il valore del campo `lowerLimit`.
   */
  public Double getLowerLimit() {
    return this.lowerLimit;
  }

  /**
   * Getter per il campo `autoAdjust`.
   *
   * @return Il valore del campo `autoAdjust`.
   */
  public Boolean getAutoAdjust() {
    return this.autoAdjust;
  }

  /**
   * Getter per il campo `sampleSize`.
   *
   * @return Il valore del campo `sampleSize`.
   */
  public Integer getSampleSize() {
    return this.sampleSize;
  }

  /**
   * Getter per il campo `archived`.
   *
   * @return Il valore del campo `archived`.
   */
  public Boolean getArchived() {
    return this.archived;
  }
}
