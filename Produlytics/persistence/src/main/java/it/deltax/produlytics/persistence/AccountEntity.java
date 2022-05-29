package it.deltax.produlytics.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Questa classe rappresenta un utente salvato nella banca dati.
 */
@Entity
@Table(name = "account")
public class AccountEntity {
  /**
   * L'username dell'utente.
   */
  @Id
  @Column(name = "username", nullable = false)
  private String username;

  /**
   * Un hash della password dell'utente.
   */
  @Column(name = "hashed_password", nullable = false)
  private String hashedPassword;

  /**
   * I permessi dell'utente.
   */
  @Column(name = "administrator", nullable = false)
  private Boolean administrator;

  /**
   * Lo stato di archiviazione dell'utente.
   */
  @Column(name = "archived", nullable = false)
  private Boolean archived;

  /**
   * Costruttore senza argomenti per JPA.
   */
  protected AccountEntity() {}

  /**
   * Crea una nuova istanza di {@code AccountEntity}.
   *
   * @param username il valore per il campo {@code username}
   * @param hashedPassword il valore per il campo {@code hashedPassword}
   * @param administrator il valore per il campo {@code administrator}
   * @param archived il valore per il campo {@code archived}
   */
  public AccountEntity(
      String username, String hashedPassword, Boolean administrator, Boolean archived) {
    this.username = username;
    this.hashedPassword = hashedPassword;
    this.administrator = administrator;
    this.archived = archived;
  }

  /**
   * Getter per il campo {@code username}.
   *
   * @return il valore del campo {@code username}
   */
  public String getUsername() {
    return username;
  }

  /**
   * Getter per il campo {@code hashedPassword}.
   *
   * @return il valore del campo {@code hashedPassword}
   */
  public String getHashedPassword() {
    return hashedPassword;
  }

  /**
   * Getter per il campo {@code administrator}.
   *
   * @return il valore del campo {@code administrator}
   */
  public Boolean getAdministrator() {
    return administrator;
  }

  /**
   * Getter per il campo {@code archived}.
   *
   * @return il valore del campo {@code archived}
   */
  public Boolean getArchived() {
    return archived;
  }
}
