import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginCommand } from './login-command';

@Injectable()
export abstract class LoginAbstractService {
  public abstract login(command: LoginCommand): Observable<any>;

  public abstract isLogged(): Observable<boolean>;

  public abstract isAdmin(): Observable<boolean>;

  public abstract getUsername(): string;

  public abstract logout(): Observable<any>;
}
