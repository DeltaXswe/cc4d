package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.uibackend.accounts.web.AccountController;
import it.deltax.produlytics.uibackend.repositories.AccountRepository;
import net.minidev.json.JSONObject;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//isOk() 200
//isNoContent() 204 NO_CONTENT
//isBadRequest() 400 GENERIC
//isUnauthorized() 401 AUTHENTICATION
//isNotFound() 404 NOT_FOUND

/**
 * Test d'integrazione per le operazioni svolte dagli utenti
 * @author Leila Dardouri
 */
public class AccountTests extends DevicesTests {
	@Autowired
	private AccountController accountController;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	 private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	@Test
	void contextLoads() {
		assertThat(this.accountController).isNotNull();
		assertThat(this.accountRepository).isNotNull();
	}

	/**
	 * Testa il caso in cui l'aggiornamento della password vada a buon fine
	 * @throws Exception la nuova password non è valida o la corrente è sbagliata
	 */
	@Test
	public void testUpdatePasswordOk() throws Exception {
		this.accountRepository.save(new AccountEntity(
			"utente1",
			bCryptPasswordEncoder.encode("passwordvecchia"),
			true,
			false));
		this.accountRepository.findById("utente1").get().getHashedPassword();
		JSONObject json = new JSONObject();
		json.put("currentPassword", "passwordvecchia");
		json.put("newPassword", "passwordnuova");

		this.mockMvc.perform(put("/accounts/utente1/password")
			.contentType(MediaType.APPLICATION_JSON)
			.content(json.toString())
			.characterEncoding("utf-8"))
			.andDo(print())
			.andExpect(status().isNoContent());
	}

	/**
	 * Testa il caso in cui la nuova password non è valida
	 * @throws Exception la nuova password non è valida o la corrente è sbagliata
	 */
	@Test
	public void testUpdatePasswordInvalidNewPassword() throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		this.accountRepository.save(new AccountEntity(
			"utente1",
			encoder.encode("passwordciao"),
			true,
			false));

		JSONObject json = new JSONObject();
		json.put("currentPassword", "passwordciao");
		json.put("newPassword", "p");

		this.mockMvc.perform(put("/accounts/utente1/password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8"))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(content().string("{\"errorCode\":\"invalidNewPassword\"}"));
	}

	/**
	 * Testa il caso in cui la password corrente è sbagliata
	 * @throws Exception la nuova password non è valida o la corrente è sbagliata
	 */
	@Test
	public void testUpdatePasswordWrongOldPassword() throws Exception {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		this.accountRepository.save(new AccountEntity(
			"utente1",
			encoder.encode("passwordciao"),
			true,
			false));

		JSONObject json = new JSONObject();
		json.put("currentPassword", "passwordsbagliata");
		json.put("newPassword", "passwordnuova");

		this.mockMvc.perform(put("/accounts/utente1/password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8"))
				.andDo(print()).andExpect(status().isUnauthorized())
				.andExpect(content().string("{\"errorCode\":\"wrongCurrentPassword\"}"));;
	}
}
