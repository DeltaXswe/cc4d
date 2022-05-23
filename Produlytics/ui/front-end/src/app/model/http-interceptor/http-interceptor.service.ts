import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, Observable, of, throwError } from 'rxjs';
import {LoginAbstractService} from "../login/login-abstract.service";

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  constructor(
    public router: Router,
    private loginService: LoginAbstractService) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    let user = localStorage.getItem('accessToken');
    if (user) {
      req = req.clone({
        headers: req.headers.set('X-Auth-Token', user)
      });
    }
    req = req.clone({
      headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
    });
    return next.handle(req)
    .pipe(
      catchError(
        (error: HttpErrorResponse) => {
          if (error.status === 401) {
              this.loginService.logout();

              return of();
          }

          return throwError(() => error);
      })
    );
  }
}
