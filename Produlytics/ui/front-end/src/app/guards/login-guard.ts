import { Injectable } from "@angular/core";
import { CanActivate, Router, UrlTree } from "@angular/router";
import { LoginAbstractService } from "../model/login/login-abstract.service";
import {Observable} from "rxjs";
import {map} from "rxjs/operators";
/**
 * Questa guardia impedisce a qualsiasi utente autenticato di navigare
 * tramite URL alla pagina '/login'.
 */
@Injectable({providedIn: 'root'})
export class LoginGuard implements CanActivate{
   constructor(
     private router: Router,
     private loginService: LoginAbstractService
   ) { }

   canActivate():Observable<UrlTree | boolean> {
     return this.loginService.isLogged()
       .pipe(
         map(isLogged => {
           if (isLogged) {
             return this.router.parseUrl('');
           } else {
             return isLogged;
           }
         })
       );
   }
}
