import {Component, Inject, OnInit, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {Account} from "../../../model/admin-account/account";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {StandardError} from "../../../../lib/standard-error";
import {SaveAccountAbstractService} from "../../../model/admin-account/save-account-abstract.service";
import {AccountSaveCommand} from "../../../model/admin-account/account-save-command";
import {LoginAbstractService} from "../../../model/login/login-abstract.service";
import {Observable, tap} from "rxjs";
import {NotificationService} from "../../../utils/notification.service";

@Component({
  selector: 'app-account-form-dialog',
  templateUrl: './account-form-dialog.component.html',
  styleUrls: ['./account-form-dialog.component.css'],
  encapsulation: ViewEncapsulation.None
})
/**
 * Gestisce la modifica di un utente fornito nel campo data di {@link MAT_DIALOG_DATA} o inserimento di un nuovo utente.
 *
 * Il dato è in questa forma: data?: { account: Account }.
 */
export class AccountFormDialogComponent implements OnInit {

  formGroup: FormGroup;

  readonly editMode: boolean;

  private passwordValidators = [Validators.minLength(6), Validators.required];

  constructor(
    private matDialogRef: MatDialogRef<AccountFormDialogComponent>,
    private saveAccountService: SaveAccountAbstractService,
    private notificationService: NotificationService,
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
    if (this.editMode && loginService.getSessionInfo()?.username === data?.account.username) {
      this.formGroup.get('administrator')!.disable();
    }
  }

  /**
   * Ereditato da {@link OnInit}. Viene usato per configurare la logica di validazione del form.
   */
  ngOnInit(): void {
    const usernameField = this.formGroup.get('username')!;
    if (this.editMode) {
      usernameField.disable();
    }
  }

  /**
   * Annulla l'operazione, chiudendo la finestra di dialogo tramite {@link MatDialogRef} senza parametri.
   */
  cancel(): void {
    this.matDialogRef.close();
  }

  /**
   * Tenta la conferma dell'operazione. Se dà successo, allora chiude la finestra di dialogo indicando che
   * l'operazione ha avuto successo. Altrimenti visualizza l'errore a schermo.
   */
  confirm(): void {
    const rawValue = this.formGroup.getRawValue();
    const command: AccountSaveCommand = {
      username: rawValue.username,
      password: rawValue.password,
      administrator: rawValue.administrator
    };
    let operation: Observable<{ }>;
    if (this.editMode) {
      operation = this.updateAccount(command);
    } else {
      operation = this.insertAccount(command);
    }
    operation.subscribe({
      next: () => {
        this.matDialogRef.close(true);
      },
      error: (err: { error: StandardError }) => {
        this.showError(err.error);
      }
    });
  }

  /**
   * Se enabled è true allora imposta lo stato per abilitare alla modifica della password, altrimenti lo imposta per
   * mantenerla com'è.
   * @param enabled lo stato da imporre alla form.
   */
  toggleChangePassword(enabled: boolean): void {
    const field = this.formGroup.get('password')!;
    if (enabled) {
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

  /**
   * Tenta l'inserimento di un nuovo utente tramite un {@link AccountSaveCommand}, interfacciandosi
   * con un servizio che implementa {@link SaveAccountAbstractService}. Restituisce un observable dell'operazione.
   * @param command le informazioni del nuovo utente.
   * @private
   */
  private insertAccount(command: AccountSaveCommand): Observable<{ username: string }> {
    return this.saveAccountService.insertAccount(command)
      .pipe(tap(({username}) => {
        this.notificationService.notify(`Utente "${username}" inserito con successo`);
      }));
  }

  /**
   * Tenta la modifica di un utente esistente, tramite un {@link AccountSaveCommand}, interfacciandosi
   * con un servizio che implementa {@link SaveAccountAbstractService}. Restituisce un observable dell'operazione.
   * @param command le informazioni di modifica dell'utente.
   * @private
   */
  private updateAccount(command: AccountSaveCommand): Observable<{ }> {
    return this.saveAccountService.updateAccount(command)
      .pipe(tap(() => {
        this.notificationService.notify(`Utente "${command.username}" aggiornato con successo`);
      }));
  }

  /**
   * Incapsula la logica di visualizzazione dell'errore.
   * @param err l'errore da visualizzare.
   * @private
   */
  private showError(err: StandardError) {
    switch (err.errorCode) {
      case 'invalidPassword':
        this.formGroup.get('password')?.setErrors({invalidPassword: true});
        break;
      case 'userNotFound':
        this.formGroup.get('username')?.setErrors({userNotFound: true});
        break;
      case 'duplicateUsername':
        this.formGroup.get('username')?.setErrors({duplicateUsername: true});
        break;
      default:
        this.notificationService.unexpectedError(`Errore non riconosciuto: "${JSON.stringify(err)}"`);
    }
  }
}
