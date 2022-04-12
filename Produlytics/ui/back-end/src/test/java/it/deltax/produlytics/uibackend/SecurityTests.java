package it.deltax.produlytics.uibackend;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.uibackend.admins.devices.web.AdminsDevicesController;
import it.deltax.produlytics.uibackend.repositories.AccountRepository;
import it.deltax.produlytics.uibackend.repositories.DeviceRepository;
import it.deltax.produlytics.uibackend.security.CustomUserDetails;
import it.deltax.produlytics.uibackend.security.CustomUserDetailsService;
import it.deltax.produlytics.uibackend.security.LoginController;
import static org.assertj.core.api.Assertions.assertThat;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import org.springframework.util.Base64Utils;

import java.nio.charset.StandardCharsets;

@SpringBootTest(
	webEnvironment = SpringBootTest.WebEnvironment.MOCK
)
@ActiveProfiles("test")
@AutoConfigureMockMvc()
public class SecurityTests {
	@Autowired
	private LoginController loginController;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CustomUserDetailsService customUserDetailsService;

	@Autowired
	protected MockMvc mockMvc;

	@BeforeAll
	private static void prepareContext(@Autowired AccountRepository accountRepository) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		accountRepository.save(new AccountEntity(
			"utente1",
			encoder.encode("password1"),
			false,
			false));
	}

	@Test
	void contextLoads() {
		assertThat(this.loginController).isNotNull();
		assertThat(this.accountRepository).isNotNull();
		assertThat(this.customUserDetailsService).isNotNull();
	}

	@Test
	public void testLoginOk() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/login")
				.header(HttpHeaders.AUTHORIZATION, "Basic "
					+ Base64Utils.encodeToString("utente1:password1".getBytes()))
				).andDo(print())
			.andExpect(status().isOk())
			.andExpect(cookie().doesNotExist("remember-me"));
	}

	@Test
	public void testLoginOkRememberMe() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/login")
				.header(HttpHeaders.AUTHORIZATION, "Basic "
					+ Base64Utils.encodeToString("utente1:password1".getBytes()))
				.param("remember-me", "true")
			).andDo(print())
			.andExpect(status().isOk())
			.andExpect(cookie().exists("remember-me"));
	}

	@Test
	public void testLoginWrongUsername() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/login")
				.header(HttpHeaders.AUTHORIZATION, "Basic "
					+ Base64Utils.encodeToString("utente100:password1".getBytes()))
			).andDo(print())
			.andExpect(status().isUnauthorized());
	}

	@Test
	public void testLoginWrongPassword() throws Exception {
		this.mockMvc.perform(MockMvcRequestBuilders.get("/login")
				.header(HttpHeaders.AUTHORIZATION, "Basic "
					+ Base64Utils.encodeToString("utente1:password100".getBytes()))
			).andDo(print())
			.andExpect(status().isUnauthorized());
	}


}

