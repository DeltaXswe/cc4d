package it.deltax.produlytics.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Questa classe rappresenta una macchina salvata nella banca dati.
 */
@Entity
@Table(name = "device")
public class DeviceEntity {
  /**
   * L'identificativo della macchina.
   */
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  /**
   * Il nome della macchina.
   */
  @Column(name = "name", nullable = false)
  private String name;

  /**
   * Lo stato di attivazione della macchina.
   */
  @Column(name = "deactivated", nullable = false)
  private Boolean deactivated;

  /**
   * Lo stato di archiviazione della macchina.
   */
  @Column(name = "archived", nullable = false)
  private Boolean archived;

  /**
   * La chiave della API rilevazioni.
   */
  @Column(name = "api_key", nullable = false)
  private String apiKey;

  /**
   * Costruttore senza argomenti per JPA.
   */
  protected DeviceEntity() {}

  /**
   * Crea una nuova istanza di {@code DeviceEntity}.
   *
   * @param id il valore per il campo {@code id}
   * @param name il valore per il campo {@code name}
   * @param archived il valore per il campo {@code archived}
   * @param deactivated il valore per il campo {@code deactivated}
   * @param apiKey il valore per il campo {@code apiKey}
   */
  public DeviceEntity(
      Integer id, String name, Boolean archived, Boolean deactivated, String apiKey) {
    this.id = id;
    this.name = name;
    this.archived = archived;
    this.deactivated = deactivated;
    this.apiKey = apiKey;
  }

  /**
   * Questo costruttore crea una rilevazione senza il campo {@code id}.
   *
   * @param name il valore per il campo {@code name}
   * @param archived il valore per il campo {@code archived}
   * @param deactivated il valore per il campo {@code deactivated}
   * @param apiKey il valore per il campo {@code apiKey}
   */
  public DeviceEntity(String name, Boolean archived, Boolean deactivated, String apiKey) {
    this.name = name;
    this.archived = archived;
    this.deactivated = deactivated;
    this.apiKey = apiKey;
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
   * Getter per il campo {@code name}.
   *
   * @return il valore del campo {@code name}
   */
  public String getName() {
    return this.name;
  }

  /**
   * Getter per il campo {@code deactivated}.
   *
   * @return il valore del campo {@code deactivated}
   */
  public Boolean getDeactivated() {
    return this.deactivated;
  }

  /**
   * Getter per il campo {@code archived}.
   *
   * @return il valore del campo {@code archived}
   */
  public Boolean getArchived() {
    return this.archived;
  }

  /**
   * Getter per il campo {@code apiKey}.
   *
   * @return il valore del campo {@code apiKey}
   */
  public String getApikey() {
    return this.apiKey;
  }
}
