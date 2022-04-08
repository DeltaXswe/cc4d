package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.persistence.DeviceEntity;
import it.deltax.produlytics.uibackend.admins.accounts.web.AdminsAccountsController;
import it.deltax.produlytics.uibackend.admins.devices.web.AdminsDevicesController;
import it.deltax.produlytics.uibackend.repositories.AccountRepository;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import org.json.JSONObject;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//isOk() 200
//isNoContent() 204 NO_CONTENT
//isBadRequest() 400 GENERIC
//isUnauthorized() 401 AUTHENTICATION
//isNotFound() 404 NOT_FOUND

public class AdminTests extends UiBackendApplicationTests {
	@Autowired
	private AdminsAccountsController adminsAccountsController;

	@Autowired
	private AdminsDevicesController adminsDevicesController;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	@Override
	@Test
	void contextLoads() {
		assertThat(adminsAccountsController).isNotNull();
		assertThat(adminsDevicesController).isNotNull();
		assertThat(accountRepository).isNotNull();
		assertThat(deviceRepository).isNotNull();
	}

	@Test
	public void testInsertAccount() throws Exception {
		JSONObject json = new JSONObject();
		json.put("username", "john");
		json.put("password", "passwordcomplessa");
		json.put("administrator", "false");

		mockMvc.perform(post("/admin/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
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
			"passwordVecchia",
			true,
			false));

		JSONObject json = new JSONObject();
		json.put("newPassword", "passwordNuova");
		json.put("administrator", "false");

		mockMvc.perform(put("/admin/nomeprova")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
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

		JSONObject json = new JSONObject();
		json.put("newPassword", "p");
		json.put("administrator", "false");

		mockMvc.perform(put("/admin/nomeprova")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().string("{\"errorCode\":\"invalidNewPassword\"}"));
	}

	@Test
	public void testUpdateAccountNotFound() throws Exception {
		accountRepository.save(new AccountEntity(
			"nomeprova",
			"passwordVecchia",
			true,
			false));

		JSONObject json = new JSONObject();
		json.put("newPassword", "passwordNuova");
		json.put("administrator", "false");

		mockMvc.perform(put("/admin/nomeCheNonEsiste")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("{\"errorCode\":\"accountNotFound\"}"));
	}

	@Test
	public void testUpdateAccountNotPassword() throws Exception {
		accountRepository.save(new AccountEntity(
			"nomeprova",
			"passwordVecchia",
			true,
			false));

		JSONObject json = new JSONObject();
		json.put("administrator", "false");

		mockMvc.perform(put("/admin/nomeprova")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isNoContent());
	}

	@Test
	public void testUpdateArchiveStatusAccountOk() throws Exception {
		accountRepository.save(new AccountEntity(
			"utente1",
			"passworddd",
			true,
			false));

		JSONObject json = new JSONObject();
		json.put("archived", "true");

		mockMvc.perform(put("/admin/accounts/utente1/archived")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isNoContent());
	}

	@Test
	public void testUpdateArchiveStatusAccountNotFound() throws Exception {
		JSONObject json = new JSONObject();
		json.put("archived", "true");

		mockMvc.perform(put("/admin/accounts/utente200/archived")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("{\"errorCode\":\"accountNotFound\"}"));
	}


	@Test
	public void testGetDevices() throws Exception{
		JSONObject json = new JSONObject();
		json.put("administrator", "true");

		mockMvc.perform(get("/admin/devices")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isOk());
	}

	@Test
	public void testModifyDevice() throws Exception {
		deviceRepository.save(new DeviceEntity("Macchina1", false, false, ""));

		JSONObject json = new JSONObject();
		json.put("name", "Macchina2");

		mockMvc.perform(put("/admin/devices/1/name")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isNoContent());
	}

	@Test
	public void testModifyDeviceNotFound() throws Exception {
		deviceRepository.save(new DeviceEntity("Macchina1", false, false, ""));

		JSONObject json = new JSONObject();
		json.put("name", "Macchina2");

		mockMvc.perform(put("/admin/devices/100/name")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("{\"errorCode\":\"deviceNotFound\"}"));
	}

	@Test
	public void testModifyDeviceArchiveStatus() throws Exception {
		deviceRepository.save(new DeviceEntity("Macchina1", false, false, ""));

		JSONObject json = new JSONObject();
		json.put("archived", "true");

		mockMvc.perform(put("/admin/devices/1/archived")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isNoContent());
	}

	@Test
	public void testModifyDeviceDeactivateStatus() throws Exception {
		deviceRepository.save(new DeviceEntity("Macchina1", false, false, ""));

		JSONObject json = new JSONObject();
		json.put("deactivated", "true");

		mockMvc.perform(put("/admin/devices/1/deactivated")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isNoContent());
	}

	@Test
	public void testGetDeviceDetailsOk() throws Exception{
		mockMvc.perform(get("/admin/devices/1")
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(
				"{\"id\":1,\"name\":\"One\",\"deactivated\":false,\"archived\":false,\"apiKey\":\"\"}"));
	}

	@Test
	public void testGetDeviceDetailsNotFound() throws Exception{
		mockMvc.perform(get("/admin/devices/111")
			).andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("{\"errorCode\":\"deviceNotFound\"}"));;
	}

}
