import { Injectable } from '@angular/core';
import { Subscription } from 'rxjs';
import { Observable } from 'rxjs';

@Injectable()
export abstract class LoginAbstractService {
  public abstract login(user: string, pw: string, rememberMe: boolean): Observable<any>;

  public abstract isLogged(): boolean;

  public abstract isAdmin(): boolean;

  public abstract logout(): void;

  public abstract getUsername(): string;
}
