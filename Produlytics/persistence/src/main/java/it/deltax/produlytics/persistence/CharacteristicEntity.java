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
   * {@code true} se l'autoadjust è attivo; {@code false} altrimenti.
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
   * Crea una nuova istanza di {@code CharacteristicEntity}.
   *
   * @param id il valore per il campo {@code id}
   * @param deviceId il valore per il campo {@code deviceId}
   * @param name il valore per il campo {@code name}
   * @param upperLimit il valore per il campo {@code upperLimit}
   * @param lowerLimit il valore per il campo {@code lowerLimit}
   * @param autoAdjust il valore per il campo {@code autoAdjust}
   * @param sampleSize il valore per il campo {@code sampleSize}
   * @param archived il valore per il campo {@code archived}
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
   * Questo costruttore crea una caratteristica senza il campo {@code id}.
   *
   * @param deviceId il valore per il campo {@code deviceId}
   * @param name il valore per il campo {@code name}
   * @param upperLimit il valore per il campo {@code upperLimit}
   * @param lowerLimit il valore per il campo {@code lowerLimit}
   * @param autoAdjust il valore per il campo {@code autoAdjust}
   * @param sampleSize il valore per il campo {@code sampleSize}
   * @param archived il valore per il campo {@code archived}
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
   * Getter per il campo {@code name}.
   *
   * @return il valore del campo {@code name}
   */
  public String getName() {
    return this.name;
  }

  /**
   * Getter per il campo {@code upperLimit}.
   *
   * @return il valore del campo {@code upperLimit}
   */
  public Double getUpperLimit() {
    return this.upperLimit;
  }

  /**
   * Getter per il campo {@code lowerLimit}.
   *
   * @return il valore del campo {@code lowerLimit}
   */
  public Double getLowerLimit() {
    return this.lowerLimit;
  }

  /**
   * Getter per il campo {@code autoAdjust}.
   *
   * @return il valore del campo {@code autoAdjust}
   */
  public Boolean getAutoAdjust() {
    return this.autoAdjust;
  }

  /**
   * Getter per il campo {@code sampleSize}.
   *
   * @return il valore del campo {@code sampleSize}
   */
  public Integer getSampleSize() {
    return this.sampleSize;
  }

  /**
   * Getter per il campo {@code archived}.
   *
   * @return il valore del campo {@code archived}
   */
  public Boolean getArchived() {
    return this.archived;
  }
}
