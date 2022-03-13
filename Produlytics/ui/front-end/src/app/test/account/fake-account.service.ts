import {Injectable} from "@angular/core";
import {AccountAbstractService} from "../../model/admin-account/account-abstract.service";
import {Observable, of} from "rxjs";
import {Account} from "../../model/admin-account/account";

@Injectable()
export class FakeAccountService implements AccountAbstractService {

  private accounts: Account[] = [
    {
      username: 'billy',
      admin: true,
      archived: false
    },
    {
      username: 'britney',
      admin: false,
      archived: false
    },
    {
      username: 'bobby',
      admin: false,
      archived: true
    }
  ];

  getAccounts(): Observable<Account[]> {
    return of(this.accounts);
  }

  insertUser(rawValue: any): Observable<{ username: string }> {
    this.accounts.push(rawValue);
    return of(rawValue);
  }

}
