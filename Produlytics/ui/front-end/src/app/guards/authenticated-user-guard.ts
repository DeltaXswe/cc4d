import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { LoginAbstractService } from "../model/login/login-abstract.service";
/**
 * Questa guardia impedisce ad un utente non autenticato di navigare
 * tramite URL a qualsiasi pagina eccetto '/login'.
 */
@Injectable({providedIn: 'root'})
export class AuthenticatedUserGuard implements CanActivate{
   constructor(private router: Router, private loginService: LoginAbstractService){}

   canActivate(): boolean | UrlTree {
      if (!this.loginService.isLogged()){
         return this.router.parseUrl('/login');
      } else {
         return true;
      }
   }
}