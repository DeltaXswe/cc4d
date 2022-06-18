import { Injectable } from '@angular/core';
import {catchError, mergeMap, Observable, tap, throwError} from 'rxjs';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import { LoginAbstractService } from './login-abstract.service';
import { LoginCommand } from './login-command';
import {SessionInfo} from './session-info';

@Injectable({
  providedIn: 'root'
})
/**
 * Questo service si occupa di effettuare richieste HTTP al back-end per
 * effettuare operazioni come login e logout. Offre inoltre metodi per
 * capire se l'utente è autenticato o amministartore
 */
export class LoginService implements LoginAbstractService {

  private static USERNAME_STORAGE_KEY = 'username';
  private static ADMIN_STORAGE_KEY = 'admin';

  constructor(
    private http: HttpClient
  ) {
  }

  /**
   * Effettua una richiesta HTTP GET per effettuare un tentativo di login.
   * Nome utente e password vengono passati tramite header, mentre il rememberMe
   * viene passato tramite query.
   * @param command Contiene nome utente, password, e rememberMe
   * @returns Un {@link Observable} contente la risposta del back-end, che dovrebbe essere vuota.
   */
  login(command: LoginCommand): Observable<void> {
    const httpOptions = {
      headers: new HttpHeaders().set('Authorization', `Basic ${btoa(command.username + ':' + command.password)}`),
      params: new HttpParams().set('remember-me', command.rememberMe)
    }

    return this.http.get<void>('/login', httpOptions);
  }

  /**
   * Effettua una richiesta HTTP POST per effettuare il logout
   * @returns Un {@link Observable} contente la risposta del back-end
   */
  logout(): Observable<void>{
    sessionStorage.clear();
    return this.http.post<void>('/logout', {});
  }

  /**
   * Tenta l'autenticazione automatica se non ci sono le informazioni nel session storage.
   *
   * */
  autoLogin(): Observable<SessionInfo> {
    return this.http.get<SessionInfo>('/accounts/info')
      .pipe(
        tap(values => {
          sessionStorage.setItem(LoginService.USERNAME_STORAGE_KEY, values.username);
          sessionStorage.setItem(LoginService.ADMIN_STORAGE_KEY, values.administrator.toString());
        }),
        catchError((err: HttpErrorResponse) => {
          if (err.status === 401) {
            return this.logout()
              .pipe(
                mergeMap(() => throwError(() => err))
              );
          } else {
            return throwError(() => err);
          }
        })
      )
  }

  getSessionInfo(): SessionInfo | undefined {
    const username = sessionStorage.getItem(LoginService.USERNAME_STORAGE_KEY);
    if (!username) {
      return undefined;
    } else {
      const administrator = sessionStorage.getItem(LoginService.ADMIN_STORAGE_KEY) === 'true';
      return {
        username,
        administrator
      };
    }
  }
}
