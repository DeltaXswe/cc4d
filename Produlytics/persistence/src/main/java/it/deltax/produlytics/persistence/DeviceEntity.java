package it.deltax.produlytics.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "device")
public class DeviceEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id", nullable = false)
  private Integer id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "archived", nullable = false)
  private Boolean archived;

  @Column(name = "deactivated", nullable = false)
  private Boolean deactivated;

  @Column(name = "api_key", nullable = false)
  private String apiKey;

  protected DeviceEntity() {}

  public DeviceEntity(
      Integer id, String name, Boolean archived, Boolean deactivated, String apiKey) {
    this.id = id;
    this.name = name;
    this.archived = archived;
    this.deactivated = deactivated;
    this.apiKey = apiKey;
  }

  public DeviceEntity(String name, Boolean archived, Boolean deactivated, String apiKey) {
    this.name = name;
    this.archived = archived;
    this.deactivated = deactivated;
    this.apiKey = apiKey;
  }

  public Integer getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public Boolean getArchived() {
    return this.archived;
  }

  public Boolean getDeactivated() {
    return this.deactivated;
  }

  public String getApikey() {
    return this.apiKey;
  }
}
