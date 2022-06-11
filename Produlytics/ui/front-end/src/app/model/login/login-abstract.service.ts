import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginCommand } from './login-command';
import {LoginResponse} from "./login-response";

@Injectable()
export abstract class LoginAbstractService {
  public abstract login(command?: LoginCommand): Observable<LoginResponse>;

  public abstract isLogged(): boolean;

  public abstract isAdmin(): boolean;

  public abstract getUsername(): string;

  public abstract logout(): Observable<{}>;
}
