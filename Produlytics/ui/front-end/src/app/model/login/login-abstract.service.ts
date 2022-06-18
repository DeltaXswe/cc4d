import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { LoginCommand } from './login-command';
import {SessionInfo} from "./session-info";

@Injectable()
export abstract class LoginAbstractService {
  public abstract login(command: LoginCommand): Observable<void>;

  public abstract autoLogin(): Observable<SessionInfo>

  public abstract getSessionInfo(): SessionInfo | undefined;

  public abstract logout(): Observable<void>;
}
