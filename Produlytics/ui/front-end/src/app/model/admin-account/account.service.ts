import { Injectable } from '@angular/core';
import {AccountAbstractService} from "./account-abstract.service";
import {Observable} from "rxjs";
import {Account} from "./account";
import {HttpClient} from "@angular/common/http";

@Injectable()
export class AccountService implements AccountAbstractService {

  constructor(
    private httpClient: HttpClient
  ) { }

  getAccounts(): Observable<Account[]> {
    return this.httpClient.get<Account[]>(`admin/accounts`);
  }

  insertUser(rawValue: any): Observable<{ username: string }> {
    return this.httpClient.post<{ username: string }>(`admin/accounts`, rawValue);
  }

}
