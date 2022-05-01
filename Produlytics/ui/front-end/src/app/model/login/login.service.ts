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
export class LoginService implements LoginAbstractService{

  //TODO: da modificare a seconda se decidiamo di usare localStorage

  constructor(private http: HttpClient,
    public router: Router,
    private cookieService: CookieService) { }

  login(command: LoginCommand): Observable<any>{
    const httpOptions = {
      headers: new HttpHeaders()
        .set('Authorization', `Basic ${btoa(command.username + ':' + command.password)}`),
      params: new HttpParams()
        .set('remember-me', `${command.rememberMe}`)
    };
    return this.http.get('/login', httpOptions).pipe(
      tap((res: any) => {
        localStorage.setItem('username', command.username)
        localStorage.setItem('admin', res['admin'])
        localStorage.setItem('accessToken', res["token"]);
        this.router.navigate(['/']);
      }));
  }

  isLogged(): boolean{  //da rifare a seconda di come funziona coi cookie
    if (localStorage.getItem('accessToken'))
      return true;
    else
      return false;
  }

  isAdmin(): boolean{
    let user = localStorage.getItem('accessToken');
    if (user)
      return JSON.parse(user).isAdmin;
    else
      return false;
  }

  logout(): void{ //da vedere coi cookie
    this.cookieService.deleteAll;
  }

  getUsername(): string{
    let user = localStorage.getItem('accessToken');
    if (user)
      return JSON.parse(user).username;
    else
      return 'username'; 
  }
}
