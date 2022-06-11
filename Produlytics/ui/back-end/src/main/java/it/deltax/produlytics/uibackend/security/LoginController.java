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
  public Map<String, Object> login(HttpSession session, Authentication authentication) {
    if(authentication != null) {
      var authorities = authentication.getAuthorities();
      var adminAuthority = ProdulyticsGrantedAuthority.ADMIN.getAuthority();
      var admin = authorities.stream().anyMatch(auth -> auth.getAuthority().equals(adminAuthority));
      var username = authentication.getName();
      return Map.of(
          "accessToken", session.getId(),
          "admin", admin,
          "username", username
          );
    } else {
      return Map.of();
    }
  }
}
