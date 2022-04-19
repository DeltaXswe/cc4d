import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {AccountSaveCommand} from "./account-save-command";

@Injectable()
export abstract class SaveAccountAbstractService {

  public abstract updateAccount(command: AccountSaveCommand): Observable<{ }>;

  public abstract insertAccount(command: AccountSaveCommand): Observable<{ username: string }>;
}
