package it.deltax.produlytics.uibackend.accounts.adapters;

import it.deltax.produlytics.persistence.AccountEntity;
import it.deltax.produlytics.uibackend.accounts.business.domain.Account;
import it.deltax.produlytics.uibackend.accounts.business.domain.AccountTiny;
import it.deltax.produlytics.uibackend.accounts.business.ports.out.*;
import it.deltax.produlytics.uibackend.devices.business.domain.TinyDevice;
import it.deltax.produlytics.uibackend.repositories.AccountRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Component
public class AccountAdapter implements UpdateAccountPasswordPort,
    UpdateAccountArchiveStatusPort,
    UpdateAccountByAdminPort,
    FindAccountPort,
    InsertAccountPort,
    GetAccountsPort
{
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

    @Override
    public void updateAccountArchiveStatus(Account account){
        repo.save(new AccountEntity(
            account.username(),
            account.hashedPassword(),
            account.administrator(),
            account.archived())
        );
    }

    @Override
    public Optional<Account> findByUsername(String username) {
        return repo.findByUsername(username)
            .map(utente ->
                        new Account(
                                utente.username(),
                                utente.hashedPassword(),
                                utente.administrator(),
                                utente.archived())
                        );
    }
    @Override
    public void updateAccount(Account account){
        repo.save(new AccountEntity(
            account.username(),
            account.hashedPassword(),
            account.administrator(),
            account.archived())
        );
    }

    @Override
    public void insertAccount(Account account){
        repo.save(new AccountEntity(
                account.username(),
                account.hashedPassword(),
                account.administrator(),
                account.archived()
            )
        );
    }

    @Override
    public List<AccountTiny> getAccounts() {
        return StreamSupport.stream(repo.findAll().spliterator(), false)
            .map(account ->
                new AccountTiny(account.getUsername(), account.getAdministrator(), account.getArchived())
            )
            .collect(Collectors.toList());
    }
}