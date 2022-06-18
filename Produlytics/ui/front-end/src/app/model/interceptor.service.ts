import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from "@angular/common/http";
import {catchError, Observable, throwError} from "rxjs";
import {Router} from "@angular/router";
import {Injectable} from "@angular/core";

@Injectable({
  providedIn: 'root'
})
export class InterceptorService implements HttpInterceptor {

  constructor(
    private router: Router
  ) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (req.headers.get('Skip-Interceptor') === 'true') {
      return next.handle(req);
    } else {
      return next.handle(req)
        .pipe(
          catchError((err: HttpResponse<any>) => {
            if (err.status === 401) {
              sessionStorage.clear();
              this.router.navigate(['/login']);
            }
            return throwError(() => err);
          })
        );
    }
  }

}
