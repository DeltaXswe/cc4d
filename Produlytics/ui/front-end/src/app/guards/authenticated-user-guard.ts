import { Injectable } from "@angular/core";
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from "@angular/router";
import { Observable } from "rxjs";

@Injectable({providedIn: 'root'})
export class AuthenticatedUserGuard implements CanActivate{
   constructor(private router: Router){}

   canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): boolean | UrlTree {
      if (!localStorage.getItem('accessToken')){
         return this.router.parseUrl('/login');
      } else {
         return true;
      }
   }
}