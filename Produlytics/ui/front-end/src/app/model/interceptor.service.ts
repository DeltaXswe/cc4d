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

    console.log(`Errore inside `);
    return next.handle(req)
      .pipe(
        catchError((err: HttpResponse<any>) => {
          console.log(`Errore inside `);
          if (err.status === 401) {
            sessionStorage.clear();
            this.router.navigate(['/login']);
          }
          return throwError(() => err);
        })
      );
  }

}
