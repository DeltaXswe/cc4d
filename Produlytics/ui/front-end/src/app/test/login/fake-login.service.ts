import { Injectable } from '@angular/core';
import { LoginAbstractService } from 'src/app/model/login/login-abstract.service';
import { Login } from './login';
import { Router } from '@angular/router';
import { Observable, of, throwError } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})

export class FakeLoginService implements LoginAbstractService {

  private accounts: Login[] = [
    {
      username: 'Gianni',
      isAdmin: true,
      password: 'Gianni'
    },
    {
      username: 'Cosimo',
      isAdmin: false,
      password: 'Cosimo'
    },
    {
      username: 'deltax',
      isAdmin: true,
      password: 'deltax'
    }
  ]

  constructor(public router: Router, private matSnackBar: MatSnackBar) { }

    public login(username: string, password: string): Observable<any> {
      if(this.accounts.find(account => account.username === username &&
        this.accounts.find(account => account.password === password))){
        localStorage.setItem('accessToken', JSON.stringify(
          this.accounts.find(wow => wow.username === username))
        );
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
