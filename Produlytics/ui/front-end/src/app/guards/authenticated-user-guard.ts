import { Injectable } from "@angular/core";
import {Router, UrlTree} from "@angular/router";
import { LoginAbstractService } from "../model/login/login-abstract.service";
import {AbstractAuthenticationGuard} from "./abstract-authentication-guard";
/**
 * Questa guardia impedisce ad un utente non autenticato di navigare
 * tramite URL a qualsiasi pagina eccetto '/login'.
 */
@Injectable({providedIn: 'root'})
export class AuthenticatedUserGuard extends AbstractAuthenticationGuard {
   constructor(router: Router, loginService: LoginAbstractService) {
     super(router, loginService);
   }

   protected authorizationLevelMatcher(): true | UrlTree {
     return true;
   }
}
