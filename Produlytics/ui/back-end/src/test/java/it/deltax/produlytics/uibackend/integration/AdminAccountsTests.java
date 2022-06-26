package it.deltax.produlytics.uibackend.integration;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.uibackend.admins.accounts.web.AdminsAccountsController;
import it.deltax.produlytics.uibackend.repositories.AccountRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// isOk() 200
// isNoContent() 204 NO_CONTENT
// isBadRequest() 400 GENERIC
// isUnauthorized() 401 AUTHENTICATION
// isNotFound() 404 NOT_FOUND

/** Test d'integrazione per le operazioni svolte dagli amministratori sugli utenti */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class AdminAccountsTests {
  @Autowired protected MockMvc mockMvc;

  @Autowired private AdminsAccountsController adminsAccountsController;

  @Autowired private AccountRepository accountRepository;

  @BeforeEach
  private void prepareContext(@Autowired AccountRepository accountRepository) {
    accountRepository.save(new AccountEntity("utente1", "password1", false, false));

    accountRepository.save(new AccountEntity("utente2", "password2", true, false));
  }

  @AfterEach
  private void deleteAll(@Autowired AccountRepository accountRepository) {
    accountRepository.deleteAll();
  }

  @Test
  void contextLoads() {
    assertThat(this.adminsAccountsController).isNotNull();
    assertThat(this.accountRepository).isNotNull();
  }

  /**
   * Testa il caso in cui l'inserimento dell'utente vada a buon fine
   *
   * @throws Exception la password non è valida
   */
  @Test
  public void testInsertAccount() throws Exception {
    JSONObject json = new JSONObject();
    json.put("username", "utente3");
    json.put("password", "passwordcomplessa");
    json.put("administrator", "false");

    this.mockMvc
        .perform(
            post("/admin/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json("{\"username\":\"utente3\"}"));
  }

  /**
   * Testa il caso in cui l'utente inserito abbia lo stesso username di uno già esistente
   *
   * @throws Exception username duplicato
   */
  @Test
  public void testInsertAccountDuplicateUsername() throws Exception {
    JSONObject json = new JSONObject();
    json.put("username", "utente1");
    json.put("password", "passwordcomplessa");
    json.put("administrator", "false");

    this.mockMvc
        .perform(
            post("/admin/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().json("{\"errorCode\":\"duplicateUsername\"}"));
  }

  /**
   * Testa il caso in cui la password assegnata non è valida
   *
   * @throws Exception la password non è valida
   */
  @Test
  public void testInsertAccountNotValid() throws Exception {
    JSONObject json = new JSONObject();
    json.put("username", "john");
    json.put("password", "p");
    json.put("administrator", "false");

    this.mockMvc
        .perform(
            post("/admin/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().json("{\"errorCode\":\"invalidPassword\"}"));
  }

  /**
   * Testa il caso in cui ci sono account da ottenere
   *
   * @throws Exception
   */
  @Test
  public void testGetAccounts() throws Exception {
    JSONObject u1 =
        new JSONObject()
            .put("username", "utente1")
            .put("administrator", false)
            .put("archived", false);
    JSONObject u2 =
        new JSONObject()
            .put("username", "utente2")
            .put("administrator", true)
            .put("archived", false);
    String requestResponse = new JSONArray().put(u1).put(u2).toString();

    this.mockMvc
        .perform(get("/admin/accounts"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().json(requestResponse));
  }

  /**
   * Testa il caso in cui l'aggiornamento di un utente vada a buon fine
   *
   * @throws Exception la password assegnata non è valida o l'utente non è stato trovato
   */
  @Test
  public void testUpdateAccountOk() throws Exception {
    JSONObject json = new JSONObject();
    json.put("password", "passwordNuova");
    json.put("administrator", "false");

    this.mockMvc
        .perform(
            put("/admin/accounts/utente1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  /**
   * Testa il caso in cui l'aggiornamento di un utente avvenga con password non valida
   *
   * @throws Exception la password assegnata non è valida o l'utente non è stato trovato
   */
  @Test
  public void testUpdateAccountInvalidNewPassword() throws Exception {
    JSONObject json = new JSONObject();
    json.put("password", "p");
    json.put("administrator", "false");

    this.mockMvc
        .perform(
            put("/admin/accounts/utente1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().json("{\"errorCode\":\"invalidNewPassword\"}"));
  }

  /**
   * Testa il caso in cui l'utente da aggiornare non sia stato trovato
   *
   * @throws Exception la password assegnata non è valida o l'utente non è stato trovato
   */
  @Test
  public void testUpdateAccountNotFound() throws Exception {
    JSONObject json = new JSONObject();
    json.put("password", "passwordNuova");
    json.put("administrator", "false");

    this.mockMvc
        .perform(
            put("/admin/accounts/nomeCheNonEsiste")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(content().json("{\"errorCode\":\"accountNotFound\"}"));
  }

  /**
   * Testa il caso in cui si aggiornano solo i permessi dell'utente
   *
   * @throws Exception la password assegnata non è valida o l'utente non è stato trovato
   */
  @Test
  public void testUpdateAccountNotPassword() throws Exception {
    JSONObject json = new JSONObject();
    json.put("administrator", "false");

    this.mockMvc
        .perform(
            put("/admin/accounts/utente1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  /**
   * Testa il caso in cui l'aggiornamento dello stato di archiviazione di un utente vada a buon fine
   *
   * @throws Exception l'utente non è stato trovato
   */
  @Test
  public void testUpdateArchiveStatusAccountOk() throws Exception {
    this.mockMvc
        .perform(
            put("/admin/accounts/utente1/archived")
                .contentType(MediaType.APPLICATION_JSON)
                .content("true")
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  /**
   * Testa il caso in cui l'utente con stato di archiviazione da aggiornare non sia stato trovato
   *
   * @throws Exception l'utente non è stato trovato
   */
  @Test
  public void testUpdateArchiveStatusAccountNotFound() throws Exception {
    this.mockMvc
        .perform(
            put("/admin/accounts/utenteCheNonEsiste/archived")
                .contentType(MediaType.APPLICATION_JSON)
                .content("true")
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isNotFound())
        .andExpect(content().json("{\"errorCode\":\"accountNotFound\"}"));
  }
}
