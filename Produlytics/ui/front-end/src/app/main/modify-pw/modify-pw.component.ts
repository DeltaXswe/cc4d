import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder, FormControl, AbstractControl } from '@angular/forms';
import { MatDialogRef } from "@angular/material/dialog";
import { ViewEncapsulation } from '@angular/core';
import { LoginAbstractService } from 'src/app/model/login/login-abstract.service';
import { ModifyPwAbstractService } from 'src/app/model/modify-pw/modify-pw-abstract.service';
import { ModifyPwCommand } from 'src/app/model/modify-pw/modify-pw-command';

@Component({
  selector: 'app-modify-pw',
  templateUrl: './modify-pw.component.html',
  styleUrls: ['./modify-pw.component.css'],
  encapsulation: ViewEncapsulation.None
})
/**
 * Questo component permette all'utente di modificare la propria password.
 */
export class ModifyPwComponent implements OnInit {
  modifyPw: FormGroup;

  constructor(private matDialogRef: MatDialogRef<ModifyPwComponent>,
    private formBuilder: FormBuilder,
    private modifyPwService: ModifyPwAbstractService,
    private loginService: LoginAbstractService) {
      this.modifyPw = this.formBuilder.group({
        oldPassword: new FormControl('', Validators.required),
        newPassword: new FormControl('', [Validators.required, Validators.minLength(6)]),
        newPasswordRe: new FormControl('', Validators.required)
      }, { validators: [this.checkPasswords]})};


  ngOnInit(): void { }

  /**
   * Validatore personalizzato che controlla che:
   * - password nuova e sua ripetizione siano uguali;
   * - poassword vecchia e nuova siano diverse.
   * @param control permette di ottenere gli input dell'utente tramite control.get
   * @returns un errore appropriato o null
   */
  checkPasswords(control: AbstractControl){
      if (control){
        const oldPassword =  control.get('oldPassword')?.value;
        const newPassword =  control.get('newPassword')?.value;
        const newPasswordRe =  control.get('newPasswordRe')?.value;
        if (newPassword !== newPasswordRe) {
          control.get('newPasswordRe')?.setErrors({mismatch: true})
          return ({mismatch: true})
        } else if(oldPassword == newPassword) {
          control.get('newPassword')?.setErrors({mustBeDifferent: true})
          return ({mustBeDifferent: true})
        } else {
          control.get('newPasswordRe')?.setErrors(null);
          return null
        }
    }
    return null;
  }

  /**
   * Tramite un service che implementa {@link ModifyPwAbstractService},
   * esegue un tentativo di cambio password.
   * Passa poi la conferma di successo o di errore a {@link ToolbarComponent}
   */
  confirm(): void{
    const rawValue = this.modifyPw.getRawValue();
    const command: ModifyPwCommand = {
      currentPassword: rawValue.oldPassword,
      newPassword: rawValue.newPassword,
    }
    if (this.modifyPw.invalid) {
      return;
    }
    this.modifyPwService.modifyPw(this.loginService.getUsername(), command)
      .subscribe({
        next: (data) => this.matDialogRef.close(data),
        error: (error) => {
          if (error.error.errorCode === 'wrongCurrentPassword') {
            this.modifyPw.get('oldPassword')?.setErrors({
              wrongCurrentPassword: true
            });
          } else {
            this.matDialogRef.close(error.status);
          }
        }
      });
  }

  /**
   * Annulla l'operazione chiudendo la finestra di dialogo,
   * senza alcuna operazione aggiuntiva.
   */
  cancel(): void{
    this.matDialogRef.close();
  }
}
