package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.uibackend.accounts.web.AccountController;
import it.deltax.produlytics.uibackend.repositories.AccountRepository;
import net.minidev.json.JSONObject;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//isOk() 200
//isNoContent() 204 NO_CONTENT
//isBadRequest() 400 GENERIC
//isUnauthorized() 401 AUTHENTICATION
//isNotFound() 404 NOT_FOUND

public class AccountTests extends UiBackendApplicationTests {
	@Autowired
	private AccountController accountController;

	@Autowired
	private AccountRepository accountRepository;

	@Override
	@Test
	void contextLoads() {
		assertThat(accountController).isNotNull();
		assertThat(accountRepository).isNotNull();
	}

	@Test
	public void testUpdatePasswordOk() throws Exception {
		//dice che l'hashed è diversa; per crearla ho usato un tool online, quindi magari è colpa sua
		// perché il codice mi sembra giusto ed è il metodo matches di Bycrypt che ritorna false
		accountRepository.save(new AccountEntity(
			"utente1",
			"$2a$10$P0nUXvpIu3ISpCCadt9r6e1gYiBkWCM3noJDSljh.he7gVSjJbbxS", //passwordvecchia
			true,
			false));

		JSONObject json = new JSONObject();
		json.put("currentPassword", "passwordvecchia");
		json.put("newPassword", "passwordnuova");

		mockMvc.perform(put("/accounts/utente1/password")
			.contentType(MediaType.APPLICATION_JSON)
			.content(json.toString())
			.characterEncoding("utf-8"))
			.andDo(print())
			.andExpect(status().isNoContent());
	} //fallisce

	@Test
	public void testUpdatePasswordInvalidNewPassword() throws Exception {
		accountRepository.save(new AccountEntity(
			"utente1",
			"$2a$10$agOHp6bPzNdLi1F.yOf.QuP3jbeD1RPmrD0krDNwYabwNBg9viIre", //passwordciao
			true,
			false));

		JSONObject json = new JSONObject();
		json.put("currentPassword", "passwordciao");
		json.put("newPassword", "p");

		mockMvc.perform(put("/accounts/utente1/password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8"))
				.andDo(print()).andExpect(status().isBadRequest())
				.andExpect(content().string("{\"errorCode\":\"invalidNewPassword\"}"));
	}

	@Test
	public void testUpdatePasswordWrongOldPassword() throws Exception {
		accountRepository.save(new AccountEntity(
			"utente1",
			"$2a$10$agOHp6bPzNdLi1F.yOf.QuP3jbeD1RPmrD0krDNwYabwNBg9viIre", //passwordciao
			true,
			false));

		JSONObject json = new JSONObject();
		json.put("currentPassword", "passwordsbagliata");
		json.put("newPassword", "passwordnuova");

		mockMvc.perform(put("/accounts/utente1/password")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8"))
				.andDo(print()).andExpect(status().isUnauthorized())
				.andExpect(content().string("{\"errorCode\":\"wrongCurrentPassword\"}"));;
	}
}
