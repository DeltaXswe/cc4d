import { Injectable } from '@angular/core';
import {
  HttpErrorResponse,
  HttpEvent, HttpEventType,
  HttpHandler,
  HttpInterceptor,
  HttpRequest, HttpResponse
} from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, EMPTY, Observable, throwError } from 'rxjs';
import { LoginAbstractService } from "../login/login-abstract.service";

/**
 * Questo service intercetta ogni chiamata uscente per aggiungerci un token di
 * accesso agli header
 */
@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  constructor(
    public router: Router,
    private loginService: LoginAbstractService
  ) { }

  /**
   * Aggiunge il token di accesso agli header di ogni chiamata. Se il back-end
   * risponde con un errore 401, effettua il logout
   * @param req La richiesta uscente
   * @param next La risposta del back-end
   * @returns Un {@link Observable} della richiesta trasformata
   */
  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req)
      .pipe(
        catchError(
          (error: HttpErrorResponse) => {
            if (error.status === 401) {
              this.loginService.logout().subscribe(_ => this.router.navigate(["/login"]));
              return EMPTY;
            } else {
              return throwError(() => error);
            }
          })
      );
  }
}
