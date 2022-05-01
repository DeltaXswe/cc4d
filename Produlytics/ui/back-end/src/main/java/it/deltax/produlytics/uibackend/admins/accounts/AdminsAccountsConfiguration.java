package it.deltax.produlytics.uibackend.admins.accounts;

import it.deltax.produlytics.uibackend.accounts.business.ports.out.PasswordEncoderPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.GetTinyAccountsUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.InsertAccountUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.UpdateAccountArchiveStatusUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.in.UpdateAccountByAdminUseCase;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.FindAccountByAdminPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.GetTinyAccountsPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.InsertAccountPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.UpdateAccountArchiveStatusPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.ports.out.UpdateAccountByAdminPort;
import it.deltax.produlytics.uibackend.admins.accounts.business.services.GetTinyAccountsService;
import it.deltax.produlytics.uibackend.admins.accounts.business.services.InsertAccountService;
import it.deltax.produlytics.uibackend.admins.accounts.business.services.UpdateAccountArchiveStatusService;
import it.deltax.produlytics.uibackend.admins.accounts.business.services.UpdateAccountByAdminService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** Classe per la configurazione di Spring che descrive come creare le classi di business. */
@Configuration
public class AdminsAccountsConfiguration {
  /**
   * Crea un'istanza di GetTinyAccountsUseCase.
   *
   * @param getAccountsPort la porta per ottenere gli utenti, da passare al costruttore di
   *     GetTinyAccountsService
   * @return la nuova istanza di GetTinyAccountsService
   */
  @Bean
  GetTinyAccountsUseCase getTinyAccountsUseCase(GetTinyAccountsPort getAccountsPort) {
    return new GetTinyAccountsService(getAccountsPort);
  }

  /**
   * Crea un'istanza di InsertAccountUseCase.
   *
   * @param findAccountByAdminPort la porta per cercare un utente, da passare al costruttore di
   *     InsertAccountService
   * @param passwordEncoderPort la porta per cifrare una password, da passare al costruttore di
   *     InsertAccountService
   * @param insertAccountPort la porta per inserire un utente, da passare al costruttore di
   *     InsertAccountService
   * @return la nuova istanza di InsertAccountService
   */
  @Bean
  InsertAccountUseCase insertAccountUseCase(
      FindAccountByAdminPort findAccountByAdminPort,
      PasswordEncoderPort passwordEncoderPort,
      InsertAccountPort insertAccountPort) {
    return new InsertAccountService(findAccountByAdminPort, passwordEncoderPort, insertAccountPort);
  }

  /**
   * Crea un'istanza di UpdateAccountArchiveStatusUseCase.
   *
   * @param findAccountByAdminPort la porta per cercare un utente, da passare al costruttore di
   *     UpdateAccountArchiveStatusUseCase
   * @param updateAccArchStatusPort la porta per aggiornare lo stato di archiviazione di un
   *     utente, da passare al costruttore di UpdateAccountArchiveStatusUseCase
   * @return la nuova istanza di UpdateAccountArchiveStatusService
   */
  @Bean
  UpdateAccountArchiveStatusUseCase updateAccountArchiveStatusUseCase(
      FindAccountByAdminPort findAccountByAdminPort,
      UpdateAccountArchiveStatusPort updateAccArchStatusPort) {
    return new UpdateAccountArchiveStatusService(findAccountByAdminPort, updateAccArchStatusPort);
  }

  /**
   * Crea un'istanza di UpdateAccountByAdminUseCase.
   *
   * @param findAccountByAdminPort la porta per cercare un utente, da passare al costruttore di
   *     InsertAccountService
   * @param passwordEncoderPort la porta per cifrare una password, da passare al costruttore di
   *     InsertAccountService
   * @param updateAccountByAdminPort la porta per aggiornare un utente per conto di un
   *     amministratore, da passare al costruttore di UpdateAccountByAdminService
   * @return la nuova istanza di UpdateAccountByAdminService
   */
  @Bean
  UpdateAccountByAdminUseCase updateAccountByAdminUseCase(
      FindAccountByAdminPort findAccountByAdminPort,
      PasswordEncoderPort passwordEncoderPort,
      UpdateAccountByAdminPort updateAccountByAdminPort) {
    return new UpdateAccountByAdminService(
        findAccountByAdminPort, passwordEncoderPort, updateAccountByAdminPort);
  }
}
