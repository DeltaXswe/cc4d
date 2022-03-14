import { Injectable } from '@angular/core';
import {SaveAccountAbstractService} from "./save-account-abstract.service";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class SaveAccountService implements SaveAccountAbstractService {

  constructor(
    private httpClient: HttpClient
  ) { }

  insertAccount(rawValue: any): Observable<{ username: string }> {
    return this.httpClient.post<{ username: string }>(`admin/accounts`, rawValue);
  }

  updateAccount(username: string, rawValue: any): Observable<{}> {
    return this.httpClient.post(`admin/accounts/${username}`, rawValue);
  }


}
