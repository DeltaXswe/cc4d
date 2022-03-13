import { Injectable } from '@angular/core';
import {Observable} from "rxjs";

@Injectable()
export abstract class SaveAccountAbstractService {

  public abstract updateAccount(username: string, rawValue: any): Observable<{}>;

  public abstract insertAccount(rawValue: any): Observable<{ username: string }>;
}
