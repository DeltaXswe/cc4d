import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Account} from "../../../model/admin-account/account";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {StandardError} from "../../../../lib/standard-error";
import {SaveAccountAbstractService} from "../../../model/admin-account/save-account-abstract.service";
import {MatSnackBar} from "@angular/material/snack-bar";

@Component({
  selector: 'app-account-form-dialog',
  templateUrl: './account-form-dialog.component.html',
  styleUrls: ['./account-form-dialog.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AccountFormDialogComponent implements OnInit {

  formGroup: FormGroup;

  constructor(
    private matDialogRef: MatDialogRef<AccountFormDialogComponent>,
    private saveAccountService: SaveAccountAbstractService,
    private matSnackBar: MatSnackBar,
    formBuilder: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data?: {
      account: Account
    }
  ) {
    this.formGroup = formBuilder.group({
      username: new FormControl(data?.account.username || '', Validators.required),
      administrator: new FormControl(data?.account.administrator || false),
      password: new FormControl(data?.account.password || '', (control => {
        const length = control.value?.length;
        return length && length > 6 ? null : { invalidPassword: true }
      }))
    });
  }

  ngOnInit(): void {

    const usernameField = this.formGroup.get('username');
    if (usernameField) {
      if (this.data?.account) {
        usernameField.disable();
      }
      usernameField.valueChanges.subscribe(() => {
        if (usernameField && usernameField.hasError('duplicateUsername')) {
          usernameField.setErrors({ duplicateUsername: null });
          usernameField.updateValueAndValidity();
        }
      });
    }
  }

  cancel() {
    this.matDialogRef.close();
  }

  confirm() {
    if (this.data?.account) {
      this.saveAccountService.updateAccount(
          this.data.account.username,
          this.formGroup.getRawValue()
        ).subscribe({
          next: () => {
            this.matSnackBar.open(
              'Utente aggiornato con successo',
              'Ok'
            );
            this.matDialogRef.close(true);
          },
          error: (err: StandardError) => {
            if (err.errorCode === 'invalidPassword') {
              this.formGroup.get('password')?.setErrors({ invalidPassword: true });
            }
          }
        });
    } else {
      this.saveAccountService.insertAccount(this.formGroup.getRawValue())
        .subscribe({
          next: () => {
            this.matSnackBar.open(
              'Utente aggiornato con successo',
              'Ok'
            );
            this.matDialogRef.close(true);
          },
          error: (err: StandardError) => {
            if (err.errorCode === 'duplicateUsername') {
              this.formGroup.get('username')?.setErrors( { duplicateUsername: true } )
            }
            if (err.errorCode === 'invalidPassword') {
              this.formGroup.get('password')?.setErrors({ invalidPassword: true });
            }
          }
        });
    }
  }
}
