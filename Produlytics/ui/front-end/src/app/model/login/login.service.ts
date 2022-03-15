import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {HttpClient, HttpHeaders, HttpErrorResponse} from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class LoginService {
  endpoint: string = 'url';
  
  constructor(private http: HttpClient, public router: Router) { }

  login(user: string, pw: string){
    return this.http
    .post<any>(this.endpoint, {user, pw})
    .subscribe((res: any) => {
    localStorage.setItem('accessToken', res.token);
    });
  }

  isLogged(): boolean{
    let authToken = localStorage.getItem('accessToken');
    return authToken !== null ? true : false;
  }

  isAdmin(): boolean{
    let authToken = localStorage.getItem('accessToken');
    return authToken !== null ? true : false;
  }

  logout(){
    let removeToken = localStorage.removeItem('accessToken');
    if (removeToken == null) {
      this.router.navigate(['login']);
    }
  }
}
