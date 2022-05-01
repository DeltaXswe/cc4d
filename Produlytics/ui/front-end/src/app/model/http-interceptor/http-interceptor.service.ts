import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { CookieService } from 'ngx-cookie-service';

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  constructor(
    public router: Router,
    private cookieService: CookieService) { }

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
      tap(
        () => {},
        (error: HttpErrorResponse) => {
          if (error.status === 401) {
              localStorage.removeItem("username");
              localStorage.removeItem("admin");
              localStorage.removeItem("accessToken");
              this.router.navigate(['/login']);
          }
      })
    );
  }
}
