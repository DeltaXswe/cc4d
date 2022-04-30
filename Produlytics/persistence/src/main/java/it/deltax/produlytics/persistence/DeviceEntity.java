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
   * Crea una nuova istanza di `DeviceEntity`.
   *
   * @param id Il valore per il campo `id`.
   * @param name Il valore per il campo `name`.
   * @param archived Il valore per il campo `archived`.
   * @param deactivated Il valore per il campo `deactivated`.
   * @param apiKey Il valore per il campo `apiKey`.
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
   * Questo costruttore crea una rilevazione senza il campo `id`.
   *
   * @param name Il valore per il campo `name`.
   * @param archived Il valore per il campo `archived`.
   * @param deactivated Il valore per il campo `deactivated`.
   * @param apiKey Il valore per il campo `apiKey`.
   */
  public DeviceEntity(String name, Boolean archived, Boolean deactivated, String apiKey) {
    this.name = name;
    this.archived = archived;
    this.deactivated = deactivated;
    this.apiKey = apiKey;
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
   * Getter per il campo `name`.
   *
   * @return Il valore del campo `name`.
   */
  public String getName() {
    return this.name;
  }

  /**
   * Getter per il campo `deactivated`.
   *
   * @return Il valore del campo `deactivated`.
   */
  public Boolean getDeactivated() {
    return this.deactivated;
  }

  /**
   * Getter per il campo `archived`.
   *
   * @return Il valore del campo `archived`.
   */
  public Boolean getArchived() {
    return this.archived;
  }

  /**
   * Getter per il campo `apiKey`.
   *
   * @return Il valore del campo `apiKey`.
   */
  public String getApikey() {
    return this.apiKey;
  }
}
