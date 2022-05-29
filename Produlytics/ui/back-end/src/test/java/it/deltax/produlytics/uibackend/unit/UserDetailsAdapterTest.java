package it.deltax.produlytics.uibackend.unit;

import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.in.FindAccountUseCase;
import it.deltax.produlytics.uibackend.exceptions.BusinessException;
import it.deltax.produlytics.uibackend.security.UserDetailsAdapter;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/** Test di unitò della classe CustomAccountDetailsService */
public class UserDetailsAdapterTest {
  /** Testa il caso in cui l'utente non sia stato trovato */
  @Test
  void testUsernameNotFound() {
    UserDetailsAdapter service =
        new UserDetailsAdapter(new UserDetailsAdapterTest.FindAccountNotFoundUseCaseMock());

    UsernameNotFoundException exception =
        assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("user"));
    assert exception.getMessage().equals("usernameNotFound");
  }

  /** Testa il caso il cui l'utente sia stato trovato */
  @Test
  void testOk() {
    UserDetailsAdapter service =
        new UserDetailsAdapter(new UserDetailsAdapterTest.FindAccountUseCaseMock());

    service.loadUserByUsername("user");
  }

  // CLASSI MOCK
  static class FindAccountNotFoundUseCaseMock implements FindAccountUseCase {
    @Override
    public Optional<Account> findByUsername(String username) throws BusinessException {
      return Optional.empty();
    }
  }

  static class FindAccountUseCaseMock implements FindAccountUseCase {
    @Override
    public Optional<Account> findByUsername(String username) throws BusinessException {
      return Optional.of(
          new Account(
              "user",
              "$2a$12$rjgaW4aqVA7Jmr9ysz7bIuAiuuvG278ab8LZiuUF/MekB7FiOhPGy",
              false,
              false));
    }
  }
}
