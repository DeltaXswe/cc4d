import {Injectable} from "@angular/core";
import {AccountAbstractService} from "../../model/admin-account/account-abstract.service";
import {Observable, of, throwError} from "rxjs";
import {Account} from "../../model/admin-account/account";
import {SaveAccountAbstractService} from "../../model/admin-account/save-account-abstract.service";

@Injectable({
  providedIn: 'root'
})
export class FakeAccountService implements
  AccountAbstractService,
  SaveAccountAbstractService
{

  private accounts: Account[] = [
    {
      username: 'billy',
      administrator: true,
      archived: false,
      password: '$HDUHFDHUF'
    },
    {
      username: 'britney',
      administrator: false,
      archived: false,
      password: '$HDUHFDHUF'
    },
    {
      username: 'bobby',
      administrator: false,
      archived: true,
      password: '$HDUHFDHUF'
    }
  ];

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

  insertAccount(rawValue: any): Observable<{ username: string }> {
    if (this.accounts.find(account => account.username === rawValue.username)) {
      return throwError({
        errorCode: 'duplicateUsername'
      });
    } else {
      this.accounts.push(rawValue);
      return of(rawValue);
    }
  }

  updateAccount(username: string, rawValue: any): Observable<{}> {
    const source = this.accounts.find(source => username === source.username);
    if (source) {
      Object.assign(source, rawValue);
      return of({});
    } else {
      return throwError('Utente non trovato');
    }
  }

}
