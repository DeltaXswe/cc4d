package it.deltax.produlytics.uibackend.security;

import java.util.Map;
import javax.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Il controller per le richieste di autenticazione. */
@RestController
@RequestMapping("/login")
public class LoginController {
  /** Riceve le chiamate all'endpoint REST per l'autenticazione. */
  @GetMapping
  public Map<String, String> login(HttpSession session, Authentication authentication) {
    var authorities = authentication.getAuthorities();
    var admin =
        authorities.stream()
            .anyMatch(
                auth ->
                    auth.getAuthority().equals(ProdulyticsGrantedAuthority.ADMIN.getAuthority()));
    return Map.of("accessToken", session.getId(), "admin", Boolean.toString(admin));
  }
}
