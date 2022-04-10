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
		assertThat(this.adminsAccountsController).isNotNull();
		assertThat(this.adminsDevicesController).isNotNull();
		assertThat(this.accountRepository).isNotNull();
		assertThat(this.deviceRepository).isNotNull();
	}

	/**
	 * Testa il caso in cui l'inserimento dell'utente vada a buon fine
	 * @throws Exception la password non è valida
	 */
	@Test
	public void testInsertAccount() throws Exception {
		JSONObject json = new JSONObject();
		json.put("username", "john");
		json.put("password", "passwordcomplessa");
		json.put("administrator", "false");

		this.mockMvc.perform(post("/admin/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isOk());
	}

	/**
	 * Testa il caso in cui la password assegnata non è valida
	 * @throws Exception la password non è valida
	 */
	@Test
	public void testInsertAccountNotValid() throws Exception {
		JSONObject json = new JSONObject();
		json.put("username", "john");
		json.put("password", "p");
		json.put("administrator", "false");

		this.mockMvc.perform(post("/admin/accounts")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().string("{\"errorCode\":\"invalidPassword\"}"));
	}

	/**
	 * Testa il caso in cui ci sono account da ottenere
	 * @throws Exception
	 */
	@Test
	public void testGetAccounts() throws Exception {
		this.accountRepository.save(new AccountEntity(
			"utente1",
			"password1",
			true,
			false));
		this.accountRepository.save(new AccountEntity(
			"utente2",
			"password2",
			true,
			false));

		this.mockMvc.perform(get("/admin/accounts")
			).andDo(print())
			.andExpect(status().isOk());
	}

	/**
	 * Testa il caso in cui l'aggiornamento di un utente vada a buon fine
	 * @throws Exception la password assegnata non è valida o l'utente non è stato trovato
	 */
	@Test
	public void testUpdateAccountOk() throws Exception {
		this.accountRepository.save(new AccountEntity(
			"nomeprova",
			"passwordVecchia",
			true,
			false));

		JSONObject json = new JSONObject();
		json.put("newPassword", "passwordNuova");
		json.put("administrator", "false");

		this.mockMvc.perform(put("/admin/nomeprova")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isNoContent());
	}

	/**
	 * Testa il caso in cui l'aggiornamento di un utente avvenga con password non valida
	 * @throws Exception la password assegnata non è valida o l'utente non è stato trovato
	 */
	@Test
	public void testUpdateAccountInvalidNewPassword() throws Exception {
		this.accountRepository.save(new AccountEntity(
			"nomeprova",
			"passworddd",
			true,
			false));

		JSONObject json = new JSONObject();
		json.put("newPassword", "p");
		json.put("administrator", "false");

		this.mockMvc.perform(put("/admin/nomeprova")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isBadRequest())
			.andExpect(content().string("{\"errorCode\":\"invalidNewPassword\"}"));
	}

	/**
	 * Testa il caso in cui l'utente da aggiornare non sia stato trovato
	 * @throws Exception la password assegnata non è valida o l'utente non è stato trovato
	 */
	@Test
	public void testUpdateAccountNotFound() throws Exception {
		this.accountRepository.save(new AccountEntity(
			"nomeprova",
			"passwordVecchia",
			true,
			false));

		JSONObject json = new JSONObject();
		json.put("newPassword", "passwordNuova");
		json.put("administrator", "false");

		this.mockMvc.perform(put("/admin/nomeCheNonEsiste")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("{\"errorCode\":\"accountNotFound\"}"));
	}

	/**
	 * Testa il caso in cui si aggiornano solo i permessi dell'utente
	 * @throws Exception la password assegnata non è valida o l'utente non è stato trovato
	 */
	@Test
	public void testUpdateAccountNotPassword() throws Exception {
		this.accountRepository.save(new AccountEntity(
			"nomeprova",
			"passwordVecchia",
			true,
			false));

		JSONObject json = new JSONObject();
		json.put("administrator", "false");

		this.mockMvc.perform(put("/admin/nomeprova")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isNoContent());
	}

	/**
	 * Testa il caso in cui l'aggiornamento dello stato di archiviazione di un utente vada a buon fine
	 * @throws Exception l'utente non è stato trovato
	 */
	@Test
	public void testUpdateArchiveStatusAccountOk() throws Exception {
		this.accountRepository.save(new AccountEntity(
			"utente1",
			"passworddd",
			true,
			false));

		JSONObject json = new JSONObject();
		json.put("archived", "true");

		this.mockMvc.perform(put("/admin/accounts/utente1/archived")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isNoContent());
	}

	/**
	 * Testa il caso in cui l'utente con stato di archiviazione da aggiornare non sia stato trovato
	 * @throws Exception l'utente non è stato trovato
	 */
	@Test
	public void testUpdateArchiveStatusAccountNotFound() throws Exception {
		JSONObject json = new JSONObject();
		json.put("archived", "true");

		this.mockMvc.perform(put("/admin/accounts/utente200/archived")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("{\"errorCode\":\"accountNotFound\"}"));
	}

	/**
	 * Testa l'ottenimento delle macchine memorizzate
	 * @throws Exception
	 */
	@Test
	public void testGetDevices() throws Exception{
		JSONObject json = new JSONObject();
		json.put("administrator", "true");

		this.mockMvc.perform(get("/admin/devices")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isOk());
	}

	/**
	 * Testa la modifica del nome di una macchina
	 * @throws Exception la macchina non è stata trovata
	 */
	@Test
	public void testModifyDevice() throws Exception {
		this.deviceRepository.save(new DeviceEntity("Macchina1", false, false, ""));

		JSONObject json = new JSONObject();
		json.put("name", "Macchina2");

		this.mockMvc.perform(put("/admin/devices/1/name")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isNoContent());
	}

	/**
	 * Testa la modifica del nome di una macchina non esistente
	 * @throws Exception la macchina non è stata trovata
	 */
	@Test
	public void testModifyDeviceNotFound() throws Exception {
		this.deviceRepository.save(new DeviceEntity("Macchina1", false, false, ""));

		JSONObject json = new JSONObject();
		json.put("name", "Macchina2");

		this.mockMvc.perform(put("/admin/devices/100/name")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("{\"errorCode\":\"deviceNotFound\"}"));
	}

	/**
	 * Testa la modifica dello stato di archiviazione di una macchina
	 * @throws Exception la macchina non è stata trovata
	 */
	@Test
	public void testModifyDeviceArchiveStatus() throws Exception {
		this.deviceRepository.save(new DeviceEntity("Macchina1", false, false, ""));

		JSONObject json = new JSONObject();
		json.put("archived", "true");

		this.mockMvc.perform(put("/admin/devices/1/archived")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isNoContent());
	}

	/**
	 * Testa la modifica dello stato di archiviazione di una macchina che non esiste
	 * @throws Exception la macchina non è stata trovata
	 */
	@Test
	public void testModifyDeviceArchiveStatusNotFound() throws Exception {
		JSONObject json = new JSONObject();
		json.put("archived", "true");

		this.mockMvc.perform(put("/admin/devices/2000/archived")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("{\"errorCode\":\"deviceNotFound\"}"));
	}

	/**
	 * Testa la modifica dello stato di attivazione di una macchina
	 * @throws Exception la macchina non è stata trovata
	 */
	@Test
	public void testModifyDeviceDeactivateStatus() throws Exception {
		this.deviceRepository.save(new DeviceEntity("Macchina1", false, false, ""));

		JSONObject json = new JSONObject();
		json.put("deactivated", "true");

		this.mockMvc.perform(put("/admin/devices/1/deactivated")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isNoContent());
	}

	/**
	 * Testa la modifica dello stato di attivazione di una macchina che non esiste
	 * @throws Exception la macchina non è stata trovata
	 */
	@Test
	public void testModifyDeviceDeactivateStatusNotFound() throws Exception {
		JSONObject json = new JSONObject();
		json.put("deactivated", "true");

		this.mockMvc.perform(put("/admin/devices/3000/deactivated")
				.contentType(MediaType.APPLICATION_JSON)
				.content(json.toString())
				.characterEncoding("utf-8")
			).andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("{\"errorCode\":\"deviceNotFound\"}"));
	}

	/**
	 * Testa l'ottenimento dei dettagli di una macchina
	 * @throws Exception la macchina non è stata trovata
	 */
	@Test
	public void testGetDeviceDetailsOk() throws Exception{
		this.mockMvc.perform(get("/admin/devices/1")
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(content().string(
				"{\"id\":1,\"name\":\"One\",\"deactivated\":false,"
					+ "\"archived\":false,\"apiKey\":\"\"}"));
	}

	/**
	 * Testa l'ottenimento dei dettagli di una macchina che non esiste
	 * @throws Exception la macchina non è stata trovata
	 */
	@Test
	public void testGetDeviceDetailsNotFound() throws Exception{
		this.mockMvc.perform(get("/admin/devices/111")
			).andDo(print())
			.andExpect(status().isNotFound())
			.andExpect(content().string("{\"errorCode\":\"deviceNotFound\"}"));;
	}
}
