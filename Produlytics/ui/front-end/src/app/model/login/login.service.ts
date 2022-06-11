import { Injectable } from '@angular/core';
import {catchError, Observable, tap, throwError} from 'rxjs';
import {HttpClient, HttpErrorResponse, HttpHeaders, HttpParams} from '@angular/common/http';
import { LoginAbstractService } from './login-abstract.service';
import { LoginCommand } from './login-command';
import {CookieService} from "ngx-cookie-service";
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
    private http: HttpClient,
    private cookieService: CookieService
  ) {
  }

  /**
   * Effettua una richiesta HTTP GET per effettuare un tentativo di login.
   * Nome utente e password vengono passati tramite header, mentre il rememberMe
   * viene passato tramite query.
   * @param command Contiene nome utente, password, e rememberMe
   * @returns Un {@link Observable} contente la risposta del back-end
   */
  login(command?: LoginCommand): Observable<LoginResponse> {
    // la situazione fallback è il login con solo il token remember-me: non servono header e
    // basta mettere il remember me true
    const headers = new HttpHeaders();
    const params = new HttpParams().set('remember-me', 'true');
    let httpOptions = {
      headers,
      params
    };
    console.log('command is ' + (command?.toString() || 'undefined'));
    if (command) {
      // se c'è il command allora aggiungo gli header e sovrascrivo la scelta di remember-me
      headers.set('Authorization', `Basic ${btoa(command.username + ':' + command.password)}`);
      params.set('remember-me', command.rememberMe);
    }
    return this.http.get<LoginResponse>('/login', httpOptions).pipe(
      tap(
        (res: LoginResponse) => {
          // il server mi restituirà
          sessionStorage.setItem(LoginService.USERNAME_STORAGE_KEY, res.username)
          sessionStorage.setItem(LoginService.ADMIN_STORAGE_KEY, res.administrator.toString())
          sessionStorage.setItem(LoginService.ACCESS_TOKEN_STORAGE_KEY, res.accessToken);
        }
      ),
      catchError((err: HttpErrorResponse) => {
        if (err.status === 401) {
          return this.logout();
        } else {
          return throwError(() => err);
        }
      })
    );
  }

  /**
   * @returns True se l'utente è autenticato, false altrimenti
   */
  isLogged(): boolean {
    return !!sessionStorage.getItem('accessToken');
  }

  /**
   * @returns True se l'utente è un amministratore, false altrimenti
   */
  isAdmin(): boolean {
    return sessionStorage.getItem('admin') === 'true';
  }

  /**
   * Effettua una richiesta HTTP POST per effettuare il logout
   * @returns Un {@link Observable} contente la risposta del back-end
   */
  logout(): Observable<any>{
    sessionStorage.clear();
    return this.http.post('/logout', {});
  }

  /**
   * @returns Il nome utente
   */
  getUsername(): string{
    return sessionStorage.getItem('username') || '';
  }
}
