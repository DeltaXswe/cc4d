import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { ModifyPwComponent } from 'src/app/main/modify-pw/modify-pw.component';
import { LoginAbstractService } from 'src/app/model/login/login-abstract.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css'],
  encapsulation: ViewEncapsulation.None
})

/**
 * Questo component costituisce la barra di navigazione.
 */
export class ToolbarComponent implements OnInit {

  constructor(
    private dialog: MatDialog,
    private loginService: LoginAbstractService
    ) { }

    ngOnInit() {
    }

    /**
     * Apre la finestra di dialogo di modifica password {@link ModifyPwComponent}
     */
    openPwDialog(): void{
      const dialogRef = this.dialog.open(ModifyPwComponent);
    }

    /**
     * Tramite un service che implementa {@link LoginAbstractService},
     * ritorna true se l'utente è autenticato, false altrimenti.
     */
    isLogged(): boolean{
      return this.loginService.isLogged();
    }

    /**
     * Tramite un service che implementa {@link LoginAbstractService},
     * ritorna true se l'utente è un admin, false altrimenti.
     */
    isAdmin(): boolean{  
      return this.loginService.isAdmin();
    } 

    /**
     * Tramite un service che implementa {@link LoginAbstractService},
     * ritorna lo username dell'utente.
     */
    getUsername(): string{
      return this.loginService.getUsername();
    }

    /**
     * Tramite un service che implementa {@link LoginAbstractService},
     * effettua il logout.
     */
    logout(): void{
      this.loginService.logout();
    }
}
