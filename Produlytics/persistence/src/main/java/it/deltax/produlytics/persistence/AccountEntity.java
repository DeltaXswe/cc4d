package it.deltax.produlytics.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class AccountEntity {
	@Id
	@Column(name = "username", nullable = false)
	private String username;

	@Column(name = "hashed_password", nullable = false)
	private String hashedPassword;

	@Column(name = "administratore", nullable = false)
	private Boolean administrator;

	@Column(name = "archived", nullable = false)
	private Boolean archived;

	protected AccountEntity() {}

	public AccountEntity(String username, String hashedPassword, Boolean administrator, Boolean archived) {
		this.username = username;
		this.hashedPassword = hashedPassword;
		this.administrator = administrator;
		this.archived = archived;
	}

	public String getUsername() {
		return username;
	}

	public String getHashedPassword() {
		return hashedPassword;
	}

	public Boolean getAdministrator() {
		return administrator;
	}

	public Boolean getArchived() {
		return archived;
	}
}
