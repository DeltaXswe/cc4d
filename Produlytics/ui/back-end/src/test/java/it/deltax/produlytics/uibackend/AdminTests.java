package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.admins.web.AdminsController;
import it.deltax.produlytics.uibackend.repositories.AccountRepository;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//isOk() 200
//isNoContent() 204 NO_CONTENT
//isBadRequest() 400 GENERIC
//isUnauthorized() 401 AUTHENTICATION
//isNotFound() 404 NOT_FOUND

public class AdminTests extends UiBackendApplicationTests {
	@Autowired
	private AdminsController adminsController;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	@Override
	@Test
	void contextLoads() {
		assertThat(adminsController).isNotNull();
		assertThat(accountRepository).isNotNull();
		assertThat(deviceRepository).isNotNull();
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
	public void testGetAccounts() throws Exception {
		accountRepository.save(new AccountEntity(
			"utente1",
			"password1",
			true,
			false));
		accountRepository.save(new AccountEntity(
			"utente2",
			"password2",
			true,
			false));

		mockMvc.perform(get("/admin/accounts")
			).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	public void testUpdateAccountOk() throws Exception {
		accountRepository.save(new AccountEntity(
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
	public void testUpdateAccountInvalidNewPassword() throws Exception {
		accountRepository.save(new AccountEntity(
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
	public void testUpdateAccountNotFound() throws Exception {
		accountRepository.save(new AccountEntity(
			"nomeprova",
			"passworddd",
			true,
			false));

		String newPassword = "passwordnuova";
		mockMvc.perform(put("/admin/nomeCheNonEsiste")
				.param("newPassword", newPassword)
				.param("administrator", String.valueOf(false))
			).andDo(print())
			.andExpect(status().isNotFound());
	}

	@Test
	public void testUpdateArchiveStatusAccountOk() throws Exception {
		accountRepository.save(new AccountEntity(
			"utente1",
			"passworddd",
			true,
			false));

		mockMvc.perform(put("/admin/accounts/utente1/archived")
				.param("archived", String.valueOf(true))
			).andDo(print())
			.andExpect(status().isNoContent());
	}

	@Test
	public void testUpdateArchiveStatusAccountNotFound() throws Exception {
		mockMvc.perform(put("/admin/accounts/utente200/archived")
				.param("archived", String.valueOf(true))
			).andDo(print())
			.andExpect(status().isNotFound());
	}


	@Test
	public void testGetDevices() throws Exception{
		deviceRepository.save(new DeviceEntity("Macchina1", false, false, ""));
		deviceRepository.save(new DeviceEntity("Macchina2", true, false, ""));
		mockMvc.perform(get("/admin/devices")
				.param("administrator", String.valueOf(true)))
			.andDo(print())
			.andExpect(status().isOk());
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
	public void testModifyDeviceArchiveStatus() throws Exception {
		deviceRepository.save(new DeviceEntity("Macchina1", false, false, ""));

		mockMvc.perform(put("/admin/devices/1/archived")
				.param("archived", "true")
			).andDo(print())
			.andExpect(status().isNoContent());
	}

	@Test
	public void testModifyDeviceDeactivateStatus() throws Exception {
		deviceRepository.save(new DeviceEntity("Macchina1", false, false, ""));

		mockMvc.perform(put("/admin/devices/1/deactivated")
				.param("deactivated", "true")
			).andDo(print())
			.andExpect(status().isNoContent());
	}

	@Test
	public void testGetDeviceDetailsOk() throws Exception{
		deviceRepository.save(new DeviceEntity("Macchina1", false, false, "1"));

		mockMvc.perform(get("/admin/devices/1")
			).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	public void testGetDeviceDetailsNotFound() throws Exception{
		mockMvc.perform(get("/admin/devices/111")
			).andDo(print())
			.andExpect(status().isNotFound());
	}

}
