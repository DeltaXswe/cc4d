import { Injectable } from '@angular/core';
import { Observable, tap, BehaviorSubject } from 'rxjs';
import {HttpClient, HttpHeaders, HttpErrorResponse} from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { LoginAbstractService } from './login-abstract.service';

@Injectable({
  providedIn: 'root'
})
export class LoginService implements LoginAbstractService{

  endpoint: string = 'url';

  constructor(private http: HttpClient, public router: Router) { }

  login(user: string, pw: string): Observable<any>{
    return this.http.post<any>(this.endpoint, {user, pw}).pipe(
      tap((res: any) => {
        localStorage.setItem('accessToken', res);
        this.router.navigate(['/']);
      }));
  }

  isLogged(): boolean{
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

   logout(){
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
