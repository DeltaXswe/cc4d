import {catchError, Observable, of} from "rxjs";
import {CanActivate, Router, UrlTree} from "@angular/router";
import {map} from "rxjs/operators";
import {SessionInfo} from "../model/login/session-info";
import {LoginAbstractService} from "../model/login/login-abstract.service";

export abstract class AbstractAuthenticationGuard implements CanActivate {

  protected constructor(protected router: Router, private loginService: LoginAbstractService){}

  canActivate(): true | UrlTree | Observable<true | UrlTree> {
    const url = this.router.parseUrl('/login');
    const sessionInfo = this.loginService.getSessionInfo();
    if (sessionInfo) {
      return this.authorizationLevelMatcher(sessionInfo);
    } else {
      return this.loginService.autoLogin()
        .pipe(
          map(value => {
            return this.authorizationLevelMatcher(value);
          }),
          catchError(() => of(url))
        );
    }
  }

  protected abstract authorizationLevelMatcher(sessionInfo: SessionInfo): true | UrlTree;
}
