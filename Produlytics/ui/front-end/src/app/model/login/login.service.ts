import { Injectable } from '@angular/core';
import { Observable, tap } from 'rxjs';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoginAbstractService } from './login-abstract.service';
import { LoginCommand } from './login-command';
import { LoginResponse } from "./login-response";

@Injectable({
  providedIn: 'root'
})
/**
 * Questo service si occupa di effettuare richieste HTTP al back-end per
 * effettuare operazioni come login e logout. Offre inoltre metodi per
 * capire se l'utente è autenticato o amministartore
 */
export class LoginService implements LoginAbstractService{

  constructor(
    private http: HttpClient,
    public router: Router
  ) { }

  /**
   * Effettua una richiesta HTTP GET per effettuare un tentativo di login.
   * Nome utente e password vengono passati tramite header, mentre il rememberMe
   * viene passato tramite query.
   * @param command Contiene nome utente, password, e rememberMe
   * @returns Un {@link Observable} contente la risposta del back-end
   */
  login(command: LoginCommand): Observable<LoginResponse>{
    const httpOptions = {
      headers: new HttpHeaders()
        .set('Authorization', `Basic ${btoa(command.username + ':' + command.password)}`),
      params: new HttpParams()
        .set('remember-me', `${command.rememberMe}`)
    };
    return this.http.get<LoginResponse>('/login', httpOptions).pipe(
      tap((res: LoginResponse) => {
        sessionStorage.setItem('username', res.username)
        sessionStorage.setItem('admin', res.administrator.toString())
        sessionStorage.setItem('accessToken', res.accessToken);
        this.router.navigate(['/']);
      }));
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
