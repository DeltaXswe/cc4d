import {Component, OnInit} from '@angular/core';
import {Account} from "../../model/admin-account/account";
import {AccountAbstractService} from "../../model/admin-account/account-abstract.service";
import {MatDialog} from "@angular/material/dialog";
import {ErrorDialogComponent} from "../../components/error-dialog/error-dialog.component";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ConfirmDialogComponent} from "../../components/confirm-dialog/confirm-dialog.component";
import {AccountFormDialogComponent} from "./account-form-dialog/account-form-dialog.component";

@Component({
  selector: 'app-accounts',
  templateUrl: './accounts.component.html',
  styleUrls: ['./accounts.component.css']
})
export class AccountsComponent implements OnInit {
  readonly displayedColumns = ['username', 'admin', 'edit', 'status'];
  accounts: Account[] = [];

  constructor(
    private accountService: AccountAbstractService,
    private matDialog: MatDialog,
    private matSnackBar: MatSnackBar
  ) { }

  ngOnInit(): void {
    this.initTable();
  }

  openNewUserDialog(): void {
    const dialogRef = this.matDialog.open(AccountFormDialogComponent);
    dialogRef.afterClosed().subscribe(reload => {
      if (reload) {
        this.initTable();
      }
    })
  }

  private initTable() {
    this.accountService.getAccounts().subscribe({
      next: value => {
        this.accounts = value;
      },
      error: err => {
        this.matDialog.open(ErrorDialogComponent, {
          data: {
            message: err
          }
        });
      }
    })
  }

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

  toggleStatus(account: Account) {
    if (account.archived) {
      this.accountService.recoverAccount(account)
        .subscribe(() => {
          this.initTable();
          this.matSnackBar.open(
            'Utente ripristinato con successo',
            'Ok'
          )
        });
    } else {
      const dialogRef = this.matDialog.open(ConfirmDialogComponent, {
        data: {
          message: `L'utente ${account.username} verrà disabilitato.`
        }
      });
      dialogRef.afterClosed().subscribe(confirm => {
        if (confirm) {
          this.accountService.archiveAccount(account)
            .subscribe(() => {
              this.initTable();
              this.matSnackBar.open(
                'Utente archiviato con successo',
                'Ok'
              )
            });
        }
      })
    }
  }
}
