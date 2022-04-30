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
  @Column(name = "administratore", nullable = false)
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
   * Crea una nuova istanza di `AccountEntity`.
   *
   * @param username Il valore per il campo `username`.
   * @param hashedPassword Il valore per il campo `hashedPassword`.
   * @param administrator Il valore per il campo `administrator`.
   * @param archived Il valore per il campo `archived`.
   */
  public AccountEntity(
      String username, String hashedPassword, Boolean administrator, Boolean archived) {
    this.username = username;
    this.hashedPassword = hashedPassword;
    this.administrator = administrator;
    this.archived = archived;
  }

  /**
   * Getter per il campo `username`.
   *
   * @return Il valore del campo `username`.
   */
  public String getUsername() {
    return username;
  }

  /**
   * Getter per il campo `hashedPassword`.
   *
   * @return Il valore del campo `hashedPassword`.
   */
  public String getHashedPassword() {
    return hashedPassword;
  }

  /**
   * Getter per il campo `administrator`.
   *
   * @return Il valore del campo `administrator`.
   */
  public Boolean getAdministrator() {
    return administrator;
  }

  /**
   * Getter per il campo `archived`.
   *
   * @return Il valore del campo `archived`.
   */
  public Boolean getArchived() {
    return archived;
  }
}
