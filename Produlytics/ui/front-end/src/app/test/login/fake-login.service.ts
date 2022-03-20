import { Injectable } from '@angular/core';
import { LoginAbstractService } from 'src/app/model/login/login-abstract.service';
import { Router } from '@angular/router';
import { Observable, of, throwError } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import {users} from './users';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})

export class FakeLoginService implements LoginAbstractService {

  constructor(public router: Router, private matSnackBar: MatSnackBar, private cookieService: CookieService) { }

    public login(username: string, password: string, rememberMe: boolean): Observable<any> {
      if(users.find(account => account.username === username &&
        users.find(account => account.password === password))){
        localStorage.setItem('accessToken', JSON.stringify(
          users.find(wow => wow.username === username))
        );
        if (rememberMe)
          this.cookieService.set('PRODULYTICS_RM', 'valore');
        this.router.navigate(['/']);
        return of({});
      } else
      this.matSnackBar.open('Credenziali non valide', 'Undo', {
        duration: 3000
      });
      return throwError('Credenziali non valide');
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
