import { Injectable } from "@angular/core";
import { CanActivate, Router, UrlTree } from "@angular/router";
import { LoginAbstractService } from "../model/login/login-abstract.service";
import {map} from "rxjs/operators";
import {catchError, Observable, of} from "rxjs";
/**
 * Questa guardia impedisce a qualsiasi utente autenticato di navigare
 * tramite URL alla pagina '/login'.
 */
@Injectable({providedIn: 'root'})
export class LoginGuard implements CanActivate{
   constructor(private router: Router, private loginService: LoginAbstractService){}

   canActivate(): Observable<boolean | UrlTree> | UrlTree {
     const sessionInfo = this.loginService.getSessionInfo();
     console.log('Login Guard says hello');
     if (sessionInfo) {
       return this.router.parseUrl('');
     } else {
       return this.loginService.autoLogin()
         .pipe(
           map(() => this.router.parseUrl('')),
           catchError(() => of(true))
         );
     }
   }
}
