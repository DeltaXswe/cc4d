import { Injectable } from "@angular/core";
import { CanActivate, Router, UrlTree } from "@angular/router";
import { LoginAbstractService } from "../model/login/login-abstract.service";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";

/**
 * Questa guardia impedisce ad un utente non autenticato di navigare
 * tramite URL a qualsiasi pagina eccetto '/login'.
 */
@Injectable({providedIn: 'root'})
export class AuthenticatedUserGuard implements CanActivate{
   constructor(private router: Router, private loginService: LoginAbstractService){}

   canActivate(): Observable<boolean | UrlTree> {
     return this.loginService.isLogged()
       .pipe(
         map(isLogged => isLogged || this.router.parseUrl('/login'))
       );
   }
}
