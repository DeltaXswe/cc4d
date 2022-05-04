import { Injectable } from '@angular/core';
import {SaveAccountAbstractService} from "./save-account-abstract.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {AccountSaveCommand} from "./account-save-command";

@Injectable({
  providedIn: 'root'
})
export class SaveAccountService implements SaveAccountAbstractService {

  constructor(
    private httpClient: HttpClient
  ) { }

  insertAccount(command:AccountSaveCommand): Observable<{ username: string }> {
    return this.httpClient.post<{ username: string }>(`admin/accounts`, command);
  }

  updateAccount(command:AccountSaveCommand): Observable<{}> {
    return this.httpClient.put(`admin/accounts/${command.username}`, command);
  }

}
