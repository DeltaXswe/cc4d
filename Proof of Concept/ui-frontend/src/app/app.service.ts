import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../environments/environment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AppService {

  constructor(private httpClient: HttpClient) { }

  public get<T>(resource: string): Observable<T> {
    return this.httpClient.get<T>(environment.apiUrl + resource);
  }
}
