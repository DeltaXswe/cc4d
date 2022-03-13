import {Injectable} from "@angular/core";
import {Observable} from "rxjs";
import {Account} from "./account";

@Injectable()
export abstract class AccountAbstractService {
  public abstract getAccounts(): Observable<Account[]>;

  public abstract insertUser(rawValue: any): Observable<{username: string}>;
}
