package it.deltax.produlytics.uibackend.accounts;

import it.deltax.produlytics.uibackend.accounts.business.ports.in.FindAccountUseCase;
import it.deltax.produlytics.uibackend.accounts.business.ports.in.UpdateAccountPasswordUseCase;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.FindAccountByAdminPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordMatcherPort;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.UpdateAccountPasswordPort;
import it.deltax.produlytics.uibackend.accounts.business.services.FindAccountService;
import it.deltax.produlytics.uibackend.accounts.business.services.UpdateAccountPasswordService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Classe per la configurazione di Spring che descrive come creare le classi di business. */
@Configuration
public class AccountsConfiguration {
  /**
   * Crea un'istanza di FindAccountUseCase.
   *
   * @param findAccountPort la porta per cercare un utente, da passare al costruttore di
   *     FindAccountService
   * @return la nuova istanza di FindAccountService
   */
  @Bean
  FindAccountUseCase findAccountUseCase(
      @Qualifier("accountAdapter") FindAccountByAdminPort findAccountPort) {
    return new FindAccountService(findAccountPort);
  }

  /**
   * Crea un'istanza di UpdateAccountPasswordUseCase.
   *
   * @param findAccountPort la porta per cercare un utente, da passare al costruttore di
   *     UpdateAccountPasswordService
   * @param passwordMatcherPort la porta per confrontare due password (una in chiaro e una cifrata),
   *     da passare al costruttore di UpdateAccountPasswordService
   * @param passwordEncoderPort la porta per cifrare una password in chiaro, da passare al
   *     costruttore di UpdateAccountPasswordService
   * @param updateAccountPasswordPort la porta per aggiornare la password di un utente, da passare
   *     al costruttore di UpdateAccountPasswordService
   * @return la nuova istanza di UpdateAccountPasswordService
   */
  @Bean
  UpdateAccountPasswordUseCase updateAccountPasswordUseCase(
      @Qualifier("accountAdapter") FindAccountByAdminPort findAccountPort,
      PasswordMatcherPort passwordMatcherPort,
      PasswordEncoderPort passwordEncoderPort,
      UpdateAccountPasswordPort updateAccountPasswordPort) {
    return new UpdateAccountPasswordService(
        findAccountPort, passwordMatcherPort, passwordEncoderPort, updateAccountPasswordPort);
  }
}
