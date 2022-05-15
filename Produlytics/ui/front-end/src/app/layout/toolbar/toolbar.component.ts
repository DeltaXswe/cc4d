import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { ModifyPwComponent } from 'src/app/main/modify-pw/modify-pw.component';
import { LoginAbstractService } from 'src/app/model/login/login-abstract.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css'],
  encapsulation: ViewEncapsulation.None
})

/**
 * Questo component implementa le funzionalità della barra di navigazione.
 */
export class ToolbarComponent implements OnInit {

  constructor(
    public dialog: MatDialog,
    private loginService: LoginAbstractService,
    private matSnackBar: MatSnackBar,
    private router: Router
    ) { }

    ngOnInit() {
    }

    /**
     * Apre la finestra di dialogo di modifica password {@link ModifyPwComponent}.
     * A seconda della risposta che otterrà da {@link ModifyPwComponent},
     * informa l'utente del successo/errore tramite {@link MatSnackBar}.
     */
    openPwDialog(): void{
      const dialogRef = this.dialog.open(ModifyPwComponent);
      dialogRef.afterClosed().subscribe(data => {
        if (data == 400){
          this.matSnackBar.open('La nuova password inserita non è valida', 'Ok');
        }else if (data == 401){
          this.matSnackBar.open('La password corrente è errata', 'Ok');
        }else {
          if (data){
            this.matSnackBar.open('Password cambiata con successo', 'Ok');
          }
        }});
    }

    /**
     * Tramite un service che implementa {@link LoginAbstractService},
     * @returns true se l'utente è autenticato, false altrimenti.
     */
    isLogged(): boolean{
      return this.loginService.isLogged();
    }

    /**
     * Tramite un service che implementa {@link LoginAbstractService},
     * @returns true se l'utente è un amministratore, false altrimenti.
     */
    isAdmin(): boolean{
      return this.loginService.isAdmin();
    }

    /**
     * Tramite un service che implementa {@link LoginAbstractService},
     * @returns lo username dell'utente.
     */
    getUsername(): string{
      return this.loginService.getUsername();
    }

    /**
     * Tramite un service che implementa {@link LoginAbstractService},
     * effettua il logout.
     */
    logout(): void{
      this.loginService.logout().subscribe({
        next: () =>this.router.navigate(['/login'])
      });
    }
}