import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Account} from "./account";

@Injectable()
export abstract class AccountAbstractService {
  public abstract getAccounts(): Observable<Account[]>;

  public abstract recoverAccount(account: Account): Observable<{}>;

  public abstract archiveAccount(account: Account): Observable<{}>;
}
