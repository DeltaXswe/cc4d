import { Injectable } from "@angular/core";
import { CanActivate, Router, UrlTree } from "@angular/router";
import { LoginAbstractService } from "../model/login/login-abstract.service";
import {catchError, Observable, of} from "rxjs";
import {HttpErrorResponse} from "@angular/common/http";
import {map} from "rxjs/operators";
/**
 * Questa guardia impedisce ad un utente non autenticato di navigare
 * tramite URL a qualsiasi pagina eccetto '/login'.
 */
@Injectable({providedIn: 'root'})
export class AuthenticatedUserGuard implements CanActivate{
   constructor(private router: Router, private loginService: LoginAbstractService){}

   canActivate(): boolean | Observable<boolean | UrlTree> {
     console.log('AuthenticateUserGuard canActivate');
     if (this.loginService.isLogged()) {
       return true;
     } else {
       return this.loginService.login()
         .pipe(
           map(() => true),
           catchError(() => of(this.router.parseUrl('/login')))
         );
     }
   }
}
