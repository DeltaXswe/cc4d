import { Injectable } from '@angular/core';
import {catchError, Observable, tap, throwError} from 'rxjs';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import { LoginAbstractService } from './login-abstract.service';
import { LoginCommand } from './login-command';
import {LoginResponse} from "./login-response";

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
  private static ACCESS_TOKEN_STORAGE_KEY = 'accessToken';

  constructor(
    private http: HttpClient
  ) {
  }

  /**
   * Effettua una richiesta HTTP GET per effettuare un tentativo di login.
   * Nome utente e password vengono passati tramite header, mentre il rememberMe
   * viene passato tramite query. Se non viene fornito nessun parametro, l'autenticazione
   * sarà tentata con il cookie remember-me.
   * @param command Contiene nome utente, password, e rememberMe
   * @returns Un {@link Observable} contente la risposta del back-end
   */
  login(command?: LoginCommand): Observable<LoginResponse> {
    let httpOptions: {headers?: HttpHeaders, params?: HttpParams};
    if (command) {
      httpOptions = {
        headers: new HttpHeaders().set('Authorization', `Basic ${btoa(command.username + ':' + command.password)}`),
        params: new HttpParams().set('remember-me', command.rememberMe)
      }
    } else {
      // Non è possibile fare controlli sul cookie remember me visto che è httpOnly
      httpOptions = {
        headers: new HttpHeaders(),
        params: new HttpParams().set('remember-me', 'true')
      }
    }
    return this.http.get<LoginResponse>('/login', httpOptions).pipe(
      tap(
        (res: LoginResponse) => {
          sessionStorage.setItem(LoginService.USERNAME_STORAGE_KEY, res.username)
          sessionStorage.setItem(LoginService.ADMIN_STORAGE_KEY, res.admin.toString())
          sessionStorage.setItem(LoginService.ACCESS_TOKEN_STORAGE_KEY, res.accessToken);
        }
      ),
      catchError((err: HttpErrorResponse) => {
        if (err.status === 401) {
          this.logout();
        }
        return throwError(() => err);
      })
    );
  }

  /**
   * Effettua una richiesta HTTP POST per effettuare il logout
   * @returns Un {@link Observable} contente la risposta del back-end
   */
  logout(): Observable<{}>{
    sessionStorage.clear();
    return this.http.post('/logout', {});
  }

  /**
   * @returns True se l'utente è autenticato, false altrimenti
   */
  isLogged(): boolean {
    return !!sessionStorage.getItem(LoginService.ACCESS_TOKEN_STORAGE_KEY);
  }

  /**
   * @returns True se l'utente è un amministratore, false altrimenti
   */
  isAdmin(): boolean {
    return sessionStorage.getItem(LoginService.ADMIN_STORAGE_KEY) === 'true';
  }

  /**
   * @returns Il nome utente
   */
  getUsername(): string{
    return sessionStorage.getItem(LoginService.USERNAME_STORAGE_KEY) || '';
  }
}
