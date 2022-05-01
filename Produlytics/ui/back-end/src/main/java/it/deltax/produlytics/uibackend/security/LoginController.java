package it.deltax.produlytics.uibackend.security;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** Il controller per le richieste di autenticazione. */
@RestController
@RequestMapping("/login")
public class LoginController {
  /** Riceve le chiamate all'endpoint REST per l'autenticazione. */
  @GetMapping
  public void login() {}
}
