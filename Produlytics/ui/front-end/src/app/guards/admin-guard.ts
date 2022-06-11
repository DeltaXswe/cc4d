import { Injectable } from "@angular/core";
import { CanActivate, Router, UrlTree } from "@angular/router";
import { LoginAbstractService } from "../model/login/login-abstract.service";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";

/**
 * Questa guardia impedisce all'utente di accedere a '/gestione-macchine' e
 * '/gestione-utenti' tramite URL se l'utente non ha permessi di amministratore.
 */
@Injectable({providedIn: 'root'})
export class AdminGuard implements CanActivate{
   constructor(private router: Router, private loginService: LoginAbstractService){}

   canActivate(): Observable<boolean | UrlTree> {
     return this.loginService.isAdmin()
       .pipe(
         map(isLogged => isLogged || this.router.parseUrl(''))
       );
   }
}
