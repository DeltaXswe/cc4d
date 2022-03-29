package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.admins.web.AdminsController;
import it.deltax.produlytics.uibackend.repositories.AdminRepository;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import org.springframework.http.ResponseEntity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class AdminTest extends UiBackendApplicationTests {
	@Autowired
	private AdminsController adminsController;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private DeviceRepository deviceRepository;

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

	@Test
	public void testModifyDevice() throws Exception {
		deviceRepository.save(new DeviceEntity("Macchina1", false, false, ""));

		mockMvc.perform(put("/admin/devices/1/name")
				.param("name", "Macchina2")
			).andDo(print())
			.andExpect(status().isNoContent());
	}

	@Test
	public void testModifyDeviceNotFound() throws Exception {
		deviceRepository.save(new DeviceEntity("Macchina1", false, false, ""));

		mockMvc.perform(put("/admin/devices/100/name")
				.param("name", "Macchina2")
			).andDo(print())
			.andExpect(status().isNotFound());
	}

	@Test
	public void testModifyDeviceArchStatus() throws Exception {
		deviceRepository.save(new DeviceEntity("Macchina1", false, false, ""));

		mockMvc.perform(put("/admin/devices/1/archived")
				.param("archived", "true")
			).andDo(print())
			.andExpect(status().isNoContent());
	}
}
