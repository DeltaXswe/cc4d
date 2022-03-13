import {Component, OnInit} from '@angular/core';
import {Account} from "../../model/admin-account/account";
import {AccountAbstractService} from "../../model/admin-account/account-abstract.service";
import {MatDialog} from "@angular/material/dialog";
import {ErrorDialogComponent} from "../../components/error-dialog/error-dialog.component";

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
    private matDialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.initTable();
  }

  openNewUserDialog(): void {
    // const dialogRef = this.matDialog.open(NewUserFormDialog);
    // dialogRef.afterClosed().subscribe(value => {
    //   if (value) {
    //     this.accountService.insertUser(value)
    //       .subscribe(() => {
    //         this.initTable();
    //       });
    //   }
    // })
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
}
