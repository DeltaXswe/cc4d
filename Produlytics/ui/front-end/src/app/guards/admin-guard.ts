import {Injectable} from "@angular/core";
import {CanActivate, Router, UrlTree} from "@angular/router";
import {LoginAbstractService} from "../model/login/login-abstract.service";
import {AbstractAuthenticationGuard} from "./abstract-authentication-guard";
import {SessionInfo} from "../model/login/session-info";

/**
 * Questa guardia impedisce all'utente di accedere a '/gestione-macchine' e
 * '/gestione-utenti' tramite URL se l'utente non ha permessi di amministratore.
 */
@Injectable({providedIn: 'root'})
export class AdminGuard extends AbstractAuthenticationGuard {
  constructor(router: Router, loginService: LoginAbstractService) {
    super(router, loginService);
  }

  protected authorizationLevelMatcher(sessionInfo: SessionInfo): true | UrlTree {
    return sessionInfo.administrator || this.router.parseUrl('');
  }
}
