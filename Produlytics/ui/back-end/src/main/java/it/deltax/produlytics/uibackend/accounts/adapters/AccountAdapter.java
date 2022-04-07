package it.deltax.produlytics.uibackend.accounts.adapters;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.UpdateAccountPasswordPort;
import it.deltax.produlytics.uibackend.repositories.AccountRepository;
import org.springframework.stereotype.Component;

@Component
public class AccountAdapter implements UpdateAccountPasswordPort {
    private final AccountRepository repo;

    public AccountAdapter(AccountRepository repo) {this.repo = repo; }

    @Override
    public void updateAccountPassword(Account account){
        repo.save(new AccountEntity(
            account.username(),
            account.hashedPassword(),
            account.administrator(),
            account.archived()
        ));
    }
}