import { Injectable } from '@angular/core';
import { HttpHandler, HttpInterceptor, HttpRequest, HTTP_INTERCEPTORS } from '@angular/common/http';

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    let user = localStorage.getItem('accessToken');
    if (user) {
      const xhr = req.clone({
        headers: req.headers.set('X-Auth-Token', user)
      });
      return next.handle(xhr);
    } else {
      const xhr = req.clone({
        headers: req.headers.set('X-Requested-With', 'XMLHttpRequest')
      });
      return next.handle(xhr);
    }
  }
}