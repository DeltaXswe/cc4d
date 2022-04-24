import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { LoginAbstractService } from "../model/login/login-abstract.service";

@Injectable({providedIn: 'root'})
export class LoginGuard implements CanActivate{
   constructor(private router: Router, private loginService: LoginAbstractService){}

   canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree {
      if (!this.loginService.isLogged()){
         return true;
      } else {
         return this.router.parseUrl('');
      }
   }
}