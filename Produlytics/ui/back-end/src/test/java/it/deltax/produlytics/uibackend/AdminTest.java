package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.persistence.CharacteristicEntity;
import it.deltax.produlytics.persistence.CharacteristicEntityId;
import it.deltax.produlytics.uibackend.admins.web.AdminsController;
import it.deltax.produlytics.uibackend.devices.web.CharacteristicsController;
import it.deltax.produlytics.uibackend.repositories.AdminRepository;
import it.deltax.produlytics.uibackend.repositories.UnarchivedCharacteristicRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;
import org.springframework.http.ResponseEntity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class AdminTest extends UiBackendApplicationTests {
	@Autowired
	private AdminsController adminsController;

	@Autowired
	private AdminRepository adminRepository;

	@Override
	@Test
	void contextLoads() {
		assertThat(adminsController).isNotNull();
		assertThat(adminRepository).isNotNull();
	}

	@Test
	public void testInsertAccount() throws Exception {
		String username = "utente";
		String password = "passwordcomplessa";
		boolean administrator = true;
		mockMvc.perform(post("/admin/accounts")
				.param("username", username)
				.param("password", password)
				.param("administrator", String.valueOf(administrator))
			).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	public void testModifyAccountOk() throws Exception {
		adminRepository.save(new AccountEntity(
			"nomeprova",
			"passworddd",
			true,
			false));

		String password = "passwordCambiata";
		boolean administrator = false;
		mockMvc.perform(put("/admin/nomeprova")
				.param("newPassword", password)
				.param("administrator", String.valueOf(administrator))
			).andDo(print())
			.andExpect(status().isNoContent());
	}

	@Test
	public void testModifyAccountInvalidNewPassword() throws Exception {
		adminRepository.save(new AccountEntity(
			"nomeprova",
			"passworddd",
			true,
			false));

		String newPassword = "p";
		boolean administrator = false;
		mockMvc.perform(put("/admin/nomeprova")
				.param("newPassword", newPassword)
				.param("administrator", String.valueOf(administrator))
			).andDo(print())
			.andExpect(status().isBadRequest());
	}

	@Test
	public void testModifyAccountNotFound() throws Exception {
		adminRepository.save(new AccountEntity(
			"nomeprova",
			"passworddd",
			true,
			false));

		String newPassword = "passwordnuova";
		boolean administrator = false;
		mockMvc.perform(put("/admin/nomeCheNonEsiste")
				.param("newPassword", newPassword)
				.param("administrator", String.valueOf(administrator))
			).andDo(print())
			.andExpect(status().isNotFound());
	}

}
