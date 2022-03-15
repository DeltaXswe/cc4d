import {Injectable} from "@angular/core";
import {AccountAbstractService} from "../../model/admin-account/account-abstract.service";
import {Observable, of, throwError} from "rxjs";
import {Account} from "../../model/admin-account/account";
import {SaveAccountAbstractService} from "../../model/admin-account/save-account-abstract.service";
import {AccountSaveCommand} from "../../model/admin-account/account-save-command";

class AccountEntity {
  username: string;
  administrator: boolean;
  archived: boolean;
  password: string = 'START';

  constructor(account: Account) {
    this.username = account.username;
    this.administrator = account.administrator;
    this.archived = account.archived;
  }

  static CREATE(command: AccountSaveCommand): AccountEntity {
    const nova = new AccountEntity({
      username: command.username,
      archived: false,
      administrator: command.administrator
    });
    nova.password = command.password!;
    return nova;
  }

  update(command: AccountSaveCommand): void {
    this.username = command.username;
    this.administrator = command.administrator;
    if (command.password) { this.password = command.password; }
  }
}

@Injectable({
  providedIn: 'root'
})
export class FakeAccountService implements
  AccountAbstractService,
  SaveAccountAbstractService
{

  private accounts: AccountEntity[] = [
    {
      username: 'billy',
      administrator: true,
      archived: false
    },
    {
      username: 'britney',
      administrator: false,
      archived: false
    },
    {
      username: 'bobby',
      administrator: false,
      archived: true
    }
  ].map(account => new AccountEntity(account));

  constructor() {
    console.log('Sono stato creato aaaaaaaaa end my sufferings');
  }

  getAccounts(): Observable<Account[]> {
    return of(this.accounts);
  }

  archiveAccount(account: Account): Observable<{}> {
    const source = this.accounts.find(source => account.username === source.username);
    if (source) {
      source.archived = true;
      return of({});
    } else {
      return throwError('Utente non trovato');
    }
  }

  recoverAccount(account: Account): Observable<{}> {
    const source = this.accounts.find(source => account.username === source.username);
    if (source) {
      source.archived = false;
      return of({});
    } else {
      return throwError('Utente non trovato');
    }
  }

  insertAccount(command: AccountSaveCommand): Observable<{ username: string }> {
    if (this.accounts.find(account => account.username === command.username)) {
      return throwError({
        errorCode: 'duplicateUsername'
      });
    } else {
      this.accounts.push(AccountEntity.CREATE(command));
      return of(command);
    }
  }

  updateAccount(command: AccountSaveCommand): Observable<{}> {
    const source = this.accounts.find(source => command.username === source.username);
    if (source) {
      source.update(command);
      return of({});
    } else {
      return throwError('Utente non trovato');
    }
  }

}
