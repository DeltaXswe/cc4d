import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Account} from "../../../model/admin-account/account";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {StandardError} from "../../../../lib/standard-error";
import {SaveAccountAbstractService} from "../../../model/admin-account/save-account-abstract.service";
import {MatSnackBar} from "@angular/material/snack-bar";
import {AccountSaveCommand} from "../../../model/admin-account/account-save-command";
import {MatSlideToggleChange} from "@angular/material/slide-toggle";
import {LoginAbstractService} from "../../../model/login/login-abstract.service";

@Component({
  selector: 'app-account-form-dialog',
  templateUrl: './account-form-dialog.component.html',
  styleUrls: ['./account-form-dialog.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class AccountFormDialogComponent implements OnInit {

  readonly formGroup: FormGroup;
  private readonly passwordValidators = [Validators.minLength(6), Validators.required];
  readonly editMode: boolean;

  constructor(
    private matDialogRef: MatDialogRef<AccountFormDialogComponent>,
    private saveAccountService: SaveAccountAbstractService,
    private matSnackBar: MatSnackBar,
    loginService: LoginAbstractService,
    formBuilder: FormBuilder,
    @Inject(MAT_DIALOG_DATA) public data?: { account: Account }
  ) {
    this.editMode = Boolean(data?.account);
    const passwordState = {
      value: null,
      disabled: this.editMode
    };
    this.formGroup = formBuilder.group({
      username: new FormControl(data?.account.username || '', Validators.required),
      administrator: new FormControl(data?.account.administrator || false),
      password: new FormControl(passwordState, this.editMode
        ? Validators.nullValidator
        : this.passwordValidators
      )
    });
    if (this.editMode && loginService.getUsername() === data?.account.username) {
      this.formGroup.get('administrator')!.disable();
    }
  }

  ngOnInit(): void {
    const usernameField = this.formGroup.get('username')!;
    if (this.editMode) {
      usernameField.disable();
    }
    usernameField.valueChanges.subscribe(() => {
      if (usernameField && usernameField.hasError('duplicateUsername')) {
        usernameField.setErrors({ duplicateUsername: null });
        usernameField.updateValueAndValidity();
      }
    });
  }

  cancel() {
    this.matDialogRef.close();
  }

  confirm() {
    const rawValue = this.formGroup.getRawValue();
    const command: AccountSaveCommand = {
      username: rawValue.username,
      password: rawValue.password,
      administrator: rawValue.administrator
    };
    if (this.editMode) {
      this.updateAccount(command);
    } else {
      this.insertAccount(command);
    }
  }

  private insertAccount(command: AccountSaveCommand) {
    this.saveAccountService.insertAccount(command)
      .subscribe({
        next: () => {
          this.matSnackBar.open(
            'Utente inserito con successo',
            'Ok'
          );
          this.matDialogRef.close(true);
        },
        error: (err: StandardError) => {
          if (err.errorCode === 'duplicateUsername') {
            this.formGroup.get('username')?.setErrors({duplicateUsername: true})
          }
          if (err.errorCode === 'invalidPassword') {
            this.formGroup.get('password')?.setErrors({invalidPassword: true});
          }
        }
      });
  }

  private updateAccount(command: AccountSaveCommand) {
    this.saveAccountService.updateAccount(command).subscribe({
      next: () => {
        this.matSnackBar.open(
          'Utente aggiornato con successo',
          'Ok'
        );
        this.matDialogRef.close(true);
      },
      error: (err: StandardError) => {
        if (err.errorCode === 'invalidPassword') {
          this.formGroup.get('password')?.setErrors({invalidPassword: true});
        }
      }
    });
  }

  changePassword(toggleChange: MatSlideToggleChange) {
    const field = this.formGroup.get('password')!;
    if (toggleChange.checked) {
      field.enable();
      field.setValidators(this.passwordValidators);
      field.updateValueAndValidity();
    } else {
      field.setValue(null);
      field.disable();
      field.removeValidators(this.passwordValidators);
      field.updateValueAndValidity();
    }
  }
}
