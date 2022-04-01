package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.uibackend.accounts.web.AccountController;
import it.deltax.produlytics.uibackend.repositories.AccountRepository;
import it.deltax.produlytics.uibackend.repositories.AdminRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

	@Autowired
	private AdminRepository adminRepository;

	@Override
	@Test
	void contextLoads() {
		assertThat(accountController).isNotNull();
		assertThat(accountRepository).isNotNull();
		assertThat(adminRepository).isNotNull();
	}

	@Test
	public void testUpdatePasswordOk() throws Exception {
		adminRepository.save(new AccountEntity(
			"utente1",
			"$2a$10$agOHp6bPzNdLi1F.yOf.QuP3jbeD1RPmrD0krDNwYabwNBg9viIre", //password ciao
			true,
			false));

		String currentPassword = "passwordciao";
		String newPassword = "passwordcomplessa";
		mockMvc.perform(put("/accounts/utente1/password")
			.param("currentPassword", currentPassword).param("newPassword", newPassword))
			.andDo(print()).andExpect(status().isNoContent());
	}

	@Test
	public void testUpdatePasswordInvalidNewPassword() throws Exception {
		adminRepository.save(new AccountEntity(
			"utente1",
			"$2a$10$agOHp6bPzNdLi1F.yOf.QuP3jbeD1RPmrD0krDNwYabwNBg9viIre", //passwordciao
			true,
			false));

		String currentPassword = "passworddd";
		String newPassword = "p";
		mockMvc.perform(put("/accounts/utente1/password")
				.param("currentPassword", currentPassword).param("newPassword", newPassword))
			.andDo(print()).andExpect(status().isBadRequest());
	}

	@Test
	public void testUpdatePasswordWrongOldPassword() throws Exception {
		adminRepository.save(new AccountEntity(
			"utente1",
			"$2a$10$agOHp6bPzNdLi1F.yOf.QuP3jbeD1RPmrD0krDNwYabwNBg9viIre", //passwordciao
			true,
			false));

		String currentPassword = "passwordmiao";
		String newPassword = "password1010";
		mockMvc.perform(put("/accounts/utente1/password")
				.param("currentPassword", currentPassword).param("newPassword", newPassword))
			.andDo(print()).andExpect(status().isUnauthorized());
	}
}
