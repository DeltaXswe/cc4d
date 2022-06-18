package it.deltax.produlytics.uibackend.integration;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.uibackend.accounts.web.AccountController;
import it.deltax.produlytics.uibackend.repositories.AccountRepository;
import it.deltax.produlytics.uibackend.security.ProdulyticsGrantedAuthority;
import java.security.Principal;
import java.util.List;
import net.minidev.json.JSONObject;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

// isOk() 200
// isNoContent() 204 NO_CONTENT
// isBadRequest() 400 GENERIC
// isUnauthorized() 401 AUTHENTICATION
// isNotFound() 404 NOT_FOUND

/** Test d'integrazione per le operazioni svolte dagli utenti e svolte sull'endpoint /accounts */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ActiveProfiles("test")
@AutoConfigureMockMvc(addFilters = false)
public class AccountTests {
  @Autowired protected MockMvc mockMvc;

  @Autowired private WebApplicationContext context;

  @Autowired private AccountController accountController;

  @Autowired private AccountRepository accountRepository;

  @Autowired private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Test
  void contextLoads() {
    assertThat(this.accountController).isNotNull();
    assertThat(this.accountRepository).isNotNull();
  }

  /**
   * Testa il caso in cui l'aggiornamento della password vada a buon fine
   *
   * @throws Exception la nuova password non è valida o la corrente è sbagliata
   */
  @Test
  public void testUpdatePasswordOk() throws Exception {
    this.accountRepository.save(
        new AccountEntity("utente1", bCryptPasswordEncoder.encode("passwordvecchia"), true, false));
    this.accountRepository.findById("utente1").get().getHashedPassword();
    JSONObject json = new JSONObject();
    json.put("currentPassword", "passwordvecchia");
    json.put("newPassword", "passwordnuova");

    this.mockMvc
        .perform(
            put("/accounts/utente1/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isNoContent());
  }

  /**
   * Testa il caso in cui la nuova password non è valida
   *
   * @throws Exception la nuova password non è valida o la corrente è sbagliata
   */
  @Test
  public void testUpdatePasswordInvalidNewPassword() throws Exception {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    this.accountRepository.save(
        new AccountEntity("utente1", encoder.encode("passwordciao"), true, false));

    JSONObject json = new JSONObject();
    json.put("currentPassword", "passwordciao");
    json.put("newPassword", "p");

    this.mockMvc
        .perform(
            put("/accounts/utente1/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isBadRequest())
        .andExpect(content().string("{\"errorCode\":\"invalidNewPassword\"}"));
  }

  /**
   * Testa il caso in cui la password corrente è sbagliata
   *
   * @throws Exception la nuova password non è valida o la corrente è sbagliata
   */
  @Test
  public void testUpdatePasswordWrongOldPassword() throws Exception {
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    this.accountRepository.save(
        new AccountEntity("utente1", encoder.encode("passwordciao"), true, false));

    JSONObject json = new JSONObject();
    json.put("currentPassword", "passwordsbagliata");
    json.put("newPassword", "passwordnuova");

    this.mockMvc
        .perform(
            put("/accounts/utente1/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json.toString())
                .characterEncoding("utf-8"))
        .andDo(print())
        .andExpect(status().isForbidden())
        .andExpect(content().string("{\"errorCode\":\"wrongCurrentPassword\"}"));
  }

  private MockMvc securityMockMvc(GrantedAuthority... authorities) {
    Principal principal = () -> "utente1";
    Authentication authentication =
        new UsernamePasswordAuthenticationToken(principal, "password", List.of(authorities));
    SecurityContext securityContext = mock(SecurityContext.class);
    when(securityContext.getAuthentication()).thenReturn(authentication);
    SecurityContextHolder.setContext(securityContext);
    return MockMvcBuilders.webAppContextSetup(this.context).apply(springSecurity()).build();
  }

  /**
   * Testa l'endpoint per ottenere i dati dell'utente corrente.
   *
   * @throws Exception se il test fallisce
   */
  @Test
  public void testGetAccountInfo() throws Exception {
    this.securityMockMvc(ProdulyticsGrantedAuthority.ACCOUNT)
        .perform(get("/accounts/info"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("{\"username\":\"utente1\",\"administrator\":false}"));
  }

  /**
   * Testa l'endpoint per ottenere i dati dell'utente corrente admin.
   *
   * @throws Exception se il test fallisce
   */
  @Test
  public void testGetAccountInfoAdmin() throws Exception {
    this.securityMockMvc(ProdulyticsGrantedAuthority.ACCOUNT, ProdulyticsGrantedAuthority.ADMIN)
        .perform(get("/accounts/info"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(content().string("{\"username\":\"utente1\",\"administrator\":true}"));
  }
}
