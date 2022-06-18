import {Component, OnInit} from '@angular/core';
import {Account} from "../../model/admin-account/account";
import {AccountAbstractService} from "../../model/admin-account/account-abstract.service";
import {MatDialog} from "@angular/material/dialog";
import {MatSnackBar} from "@angular/material/snack-bar";
import {AccountFormDialogComponent} from "./account-form-dialog/account-form-dialog.component";
import {AccountsDataSource} from "./accounts.data-source";
import {LoginAbstractService} from "../../model/login/login-abstract.service";
import {NotificationService} from "../../utils/notification.service";
import {StandardError} from "../../../lib/standard-error";

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
/**
 * Gestisce gli utenti.
 */
export class AccountsComponent implements OnInit {

  readonly displayedColumns = ['username', 'admin', 'edit', 'status'];

  accounts = new AccountsDataSource();

  constructor(
    private accountService: AccountAbstractService,
    private matDialog: MatDialog,
    private notificationService: NotificationService,
    private loginService: LoginAbstractService
  ) { }

  /**
   * Ereditato da {@link OnInit}. Viene usato per inizializzare la tabella.
   *
   */
  ngOnInit(): void {
    this.initTable();
  }

  /**
   * Apre una finestra di dialogo {@link AccountFormDialog} in modalità di creazione. Alla chiusura
   * della finestra, se l'operazione non era stata annullata e ha avuto successo vengono ricaricati
   * gli account da mostrare nella tabella.
   */
  openNewAccountDialog(): void {
    const dialogRef = this.matDialog.open(AccountFormDialogComponent);
    dialogRef.afterClosed().subscribe(reload => {
      if (reload) {
        this.initTable();
      }
    });
  }

  /**
   * Apre una finestra di dialogo {@link AccountFormDialog} in modalità di modifica, inizializzato con i
   * dati dell'utente passato come parametro. Alla chiusura
   * della finestra, se l'operazione non era stata annullata e ha avuto successo vengono ricaricati
   * gli account da mostrare nella tabella.
   * @param account l'account da modificare.
   */
  openEditAccountDialog(account: Account): void {
    const dialogRef = this.matDialog.open(AccountFormDialogComponent, {
      data: {
        account
      }
    });
    dialogRef.afterClosed().subscribe(reload => {
      if (reload) {
        this.initTable();
      }
    });
  }

  /**
   * Archivia l'account passato come parametro se non era archiviato, altrimenti lo ripristina. A
   * operazione conclusa notifica l'esito all'utente tramite una {@link MatSnackBar}.
   *
   * @param account l'account da modificare.
   */
  toggleStatus(account: Account): void {
    if (account.archived) {
      this.accountService.recoverAccount(account)
        .subscribe({
          next: () => {
            this.initTable();
            this.notificationService.notify('Utente ripristinato con successo');
          },
          error: (err: { error: StandardError }) => {
            this.notificationService.unexpectedError(`Errore imprevisto: "${JSON.stringify(err)}"`);
          }
        });
    } else {
      this.notificationService.requireConfirm(`L'utente ${account.username} verrà disabilitato.`)
        .subscribe(confirm => {
          if (confirm) {
            this.accountService.archiveAccount(account)
              .subscribe({
                next: () => {
                  this.initTable();
                  this.notificationService.notify('Utente archiviato con successo');
                },
                error: (err: { error: StandardError }) => {
                  this.notificationService.unexpectedError(`Errore imprevisto: "${JSON.stringify(err)}"`);
                }
              });
          }
      });
    }
  }

  /**
   * @returns true solo se l'utente passato come parametro è l'utente registrato.
   *
   * @param account l'utente da verificare se è registrato o meno.
   */
  isLoggedUser(account: Account): boolean {
    return account.username === this.loginService.getSessionInfo()?.username;
  }

  /**
   * Inizializza gli account da mostrare nella tabella, interfacciandosi con un servizio che implementa
   * {@link AccountAbstractService}.
   *
   * @private
   */
  private initTable(): void {
    this.accountService.getAccounts().subscribe({
      next: value => {
        this.accounts.setData(value);
      },
      error: (err: { error: StandardError }) => {
        this.notificationService.unexpectedError(`Errore imprevisto: "${JSON.stringify(err)}"`);
      }
    })
  }
}
