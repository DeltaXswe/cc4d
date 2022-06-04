import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoginAbstractService } from './login-abstract.service';
import { LoginCommand } from './login-command';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
/**
 * Questo service si occupa di effettuare richieste HTTP al back-end per
 * effettuare operazioni come login e logout. Offre inoltre metodi per
 * capire se l'utente è autenticato o amministartore
 */
export class LoginService implements LoginAbstractService{

  constructor(private http: HttpClient,
    public router: Router,
    private cookieService: CookieService) { }

  /**
   * Effettua una richiesta HTTP GET per effettuare un tentativo di login.
   * Nome utente e password vengono passati tramite header, mentre il rememberMe
   * viene passato tramite query.
   * @param command Contiene nome utente, password, e rememberMe
   * @returns Un {@link Observable} contente la risposta del back-end
   */
  login(command: LoginCommand): Observable<any>{
    const httpOptions = {
      headers: new HttpHeaders()
        .set('Authorization', `Basic ${btoa(command.username + ':' + command.password)}`),
      params: new HttpParams()
        .set('remember-me', `${command.rememberMe}`)
    };
    return this.http.get('/login', httpOptions).pipe(
      tap((res: any) => {
        sessionStorage.setItem('username', command.username)
        sessionStorage.setItem('admin', res['admin'])
        sessionStorage.setItem('accessToken', res["token"]);
        if (command.rememberMe) {
          localStorage.setItem('username', command.username)
          localStorage.setItem('admin', res['admin'])
          localStorage.setItem('accessToken', res["token"]);
        }
        this.router.navigate(['/']);
      }));
  }

  /**
   * @returns True se l'utente è autenticato, false altrimenti
   */
  isLogged(): boolean{
    if (sessionStorage.getItem('accessToken') || localStorage.getItem('accessToken')) {
      return true;
    }
    else {
      return false;
    }
  }

  /**
   * @returns True se l'utente è un amministratore, false altrimenti
   */
  isAdmin(): boolean{
    let admin = null;
    if (sessionStorage.getItem('admin')) {
      admin = sessionStorage.getItem('admin');
    } else {
      admin = localStorage.getItem('admin');
    }
    if (admin)
      return admin == "true";
    else
      return false;
  }

  /**
   * Effettua una richiesta HTTP POST per effettuare il logout
   * @returns Un {@link Observable} contente la risposta del back-end
   */
  logout(): Observable<any>{
    localStorage.clear();
    sessionStorage.clear();
    return this.http.post('/logout', {});
  }

  /**
   * @returns Il nome utente
   */
  getUsername(): string{
    let username = null;
    if (sessionStorage.getItem('username')) {
      username = sessionStorage.getItem('username');
    } else {
      username = localStorage.getItem('username');
    }
    if (username)
      return username;
    else
      return 'username';
  }
}
