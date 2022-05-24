import { Injectable } from "@angular/core";
import { CanActivate, Router, UrlTree } from "@angular/router";
import { LoginAbstractService } from "../model/login/login-abstract.service";
/**
 * Questa guardia impedisce a qualsiasi utente autenticato di navigare
 * tramite URL alla pagina '/login'.
 */
@Injectable({providedIn: 'root'})
export class LoginGuard implements CanActivate{
   constructor(private router: Router, private loginService: LoginAbstractService){}

   canActivate(): boolean | UrlTree {
      if (!this.loginService.isLogged()){
         return true;
      } else {
         return this.router.parseUrl('');
      }
   }
}
