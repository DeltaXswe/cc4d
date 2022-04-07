import { Injectable } from '@angular/core';
import { LoginAbstractService } from 'src/app/model/login/login-abstract.service';
import { Router } from '@angular/router';
import { Observable, of, throwError } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { users } from './users';
import { CookieService } from 'ngx-cookie-service';
import { LoginCommand } from 'src/app/model/login/login-command';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})

export class FakeLoginService implements LoginAbstractService {

  constructor(public router: Router,
    private matSnackBar: MatSnackBar,
    private cookieService: CookieService) { }

    public login(command: LoginCommand): Observable<any> {
      if(users.find(account => account.username === command.username &&
        users.find(account => account.password === command.password))){
        localStorage.setItem('accessToken', JSON.stringify(
          users.find(wow => wow.username === command.username))
        );
        if (command.rememberMe)
          this.cookieService.set('PRODULYTICS_RM', 'valore');
        this.router.navigate(['/']);
        return of({});
      } else {
      const error = new HttpErrorResponse({ status: 401 });
      return of(error);
      }
    }

  public isLogged(): boolean {
    if (localStorage.getItem('accessToken'))
      return true;
    else
      return false;
  }

  public isAdmin(): boolean{
    let user = localStorage.getItem('accessToken');
    if (user)
      return JSON.parse(user).isAdmin;
    else
      return false;
  }

  public logout(): void {
    localStorage.removeItem('accessToken');
    this.cookieService.delete('PRODULYTICS_RM');
    this.router.navigate(['/login']);
  }

  getUsername(){
    let user = localStorage.getItem('accessToken');
    if (user)
      return JSON.parse(user).username;
    else
      return 'username';
  }
}
