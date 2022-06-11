import {Injectable} from "@angular/core";
import {CanActivate, Router, UrlTree} from "@angular/router";
import {LoginAbstractService} from "../model/login/login-abstract.service";

/**
 * Questa guardia impedisce all'utente di accedere a '/gestione-macchine' e
 * '/gestione-utenti' tramite URL se l'utente non ha permessi di amministratore.
 */
@Injectable({providedIn: 'root'})
export class AdminGuard implements CanActivate {
  constructor(private router: Router, private loginService: LoginAbstractService) {
  }

  canActivate(): boolean | UrlTree {
    // Un miglioramento potrebbe essere: si rende questa guardia indipendente dalla guardia di autenticazione
    // e tenta anch'essa il login se l'utente non è autenticato. Potrebbe essere un'estensione della prima
    // guardia. Così fa un redirect in ogni caso sulla dashboard, il primo accesso, che di per se non è troppo male.
    if (!this.loginService.isAdmin()) {
      return this.router.parseUrl('');
    } else {
      return true;
    }
  }
}
