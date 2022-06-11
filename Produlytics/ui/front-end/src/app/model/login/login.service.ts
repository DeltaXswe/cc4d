import {Injectable} from '@angular/core';
import {Observable, of, tap} from 'rxjs';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {Router} from '@angular/router';
import {LoginAbstractService} from './login-abstract.service';
import {LoginCommand} from './login-command';
import {LoginResponse} from './login-response';
import {CookieService} from "ngx-cookie-service";
import {map} from "rxjs/operators";

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
    private router: Router,
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
          this.router.navigate(['/']);
        }
      )
    );
  }

  /**
   * @returns True se l'utente è autenticato, false altrimenti
   */
  isLogged(): Observable<boolean> {
    const sessionCookie = this.cookieService.get('PRODULYTICS_S');
    if (!sessionCookie) {
      return this.login().pipe(map(Boolean));
    } else {
      return of(!!sessionStorage.getItem(LoginService.ACCESS_TOKEN_STORAGE_KEY));
    }
  }

  /**
   * @returns True se l'utente è un amministratore, false altrimenti
   */
  isAdmin(): Observable<boolean> {
    return this.isLogged()
      .pipe(
        map(isLogged => isLogged && sessionStorage.getItem(LoginService.ADMIN_STORAGE_KEY) === 'true')
      );
  }

  /**
   * Effettua una richiesta HTTP POST per effettuare il logout
   * @returns Un {@link Observable} contente la risposta del back-end
   */
  logout(): Observable<any> {
    sessionStorage.clear();
    return this.http.post('/logout', {});
  }

  /**
   * @returns Il nome utente
   */
  getUsername(): string {
    // che sia loggato o meno non ci interessa
    return sessionStorage.getItem(LoginService.USERNAME_STORAGE_KEY)
    || 'Utente non riconosciuto'
  }
}
