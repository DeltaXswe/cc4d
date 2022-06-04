import { Injectable } from '@angular/core';
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Router } from '@angular/router';
import { catchError, Observable, throwError } from 'rxjs';
import { LoginAbstractService } from "../login/login-abstract.service";

/**
 * Questo service intercetta ogni chiamata uscente per aggiungerci un token di
 * accesso agli header
 */
@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  constructor(
    public router: Router,
    private loginService: LoginAbstractService) { }

  /**
   * Aggiunge il token di accesso agli header di ogni chiamata. Se il back-end
   * risponde con un errore 401, effettua il logout
   * @param req La richiesta uscente
   * @param next La risposta del back-end
   * @returns Un {@link Observable} della richiesta trasformata
   */
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

    /**
     * Spring Boot di default è impostato in modo tale da bloccare le richieste REST che fanno del side effect
     * (POST, PUT, DELETE, PATCH). Il modo per attivarle è mettere un header che si chiami X-XSFR-TOKEN e valorizzarlo
     * con un cookie che ci dà il server. Questo è quanto si evince dalla documentazione di Spring Boot e da questo
     * articolo https://www.baeldung.com/spring-security-csrf#2-front-end-configuration, da cui ho preso questa
     * soluzione.
     *
     * Tutto ciò è parzialmente vero perché all'inizio il server non bloccava le POST, ma solo le PUT. Quindi potrebbe
     * esserci dietro un bug di Spring Boot che non le impedisce. Resta il fatto che mettendo
     * queste due linee di codice ora tutto funziona.
     * */
    const csrfToken = document.cookie.replace(/(?:(?:^|.*;\s*)XSRF-TOKEN\s*\=\s*([^;]*).*$)|^.*$/, '$1');
    req.headers.set('X-XSRF-TOKEN', csrfToken);

    return next.handle(req)
      .pipe(
        catchError(
          (error: HttpErrorResponse) => {
            // if (error.status === 401) {
            //     this.loginService.logout();
            //
            //     return of();
            // }

            return throwError(() => error);
        })
      );
  }
}
