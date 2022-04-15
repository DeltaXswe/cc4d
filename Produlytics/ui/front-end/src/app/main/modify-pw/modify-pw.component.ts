import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder, AbstractControlOptions, ValidationErrors, FormControl, AbstractControl } from '@angular/forms';
import { MatDialogRef } from "@angular/material/dialog";
import { ViewEncapsulation } from '@angular/core';
import { LoginAbstractService } from 'src/app/model/login/login-abstract.service';
import { ModifyPwAbstractService } from 'src/app/model/modify-pw/modify-pw-abstract.service';
import { ModifyPwCommand } from 'src/app/model/modify-pw/modify-pw-command';
import { MatSnackBar } from '@angular/material/snack-bar';

@Component({
  selector: 'app-modify-pw',
  templateUrl: './modify-pw.component.html',
  styleUrls: ['./modify-pw.component.css'],
  encapsulation: ViewEncapsulation.None 
})
export class ModifyPwComponent implements OnInit {
  modifyPw: FormGroup;

  constructor(private matDialogRef: MatDialogRef<ModifyPwComponent>,
    private formBuilder: FormBuilder,
    private modifyPwService: ModifyPwAbstractService,
    private loginService: LoginAbstractService,
    private matSnackBar: MatSnackBar) {
      this.modifyPw = this.formBuilder.group({
        oldPassword: new FormControl('', Validators.required),
        newPassword: new FormControl('', [Validators.required, Validators.minLength(6)]),
        newPasswordRe: new FormControl('', Validators.required)
      }, { validators: [this.checkPasswords]})}; 

  
  ngOnInit(): void { }
  
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

  confirm(): void{
    const rawValue = this.modifyPw.getRawValue();
    const command: ModifyPwCommand = {
      oldPassword: rawValue.oldPassword,
      newPassword: rawValue.newPassword,
    }  
    if (this.modifyPw.invalid) {
      return;
    }
    this.modifyPwService.modifyPw(this.loginService.getUsername(), command)
      .subscribe({next: () => this.matSnackBar.open('La password è stata cambiata', 'Undo', {
        duration: 3000
      }), 
      error: (error) => {if (error.status == 401){
      this.matSnackBar.open('La password corrente è errata', 'Undo', {
        duration: 3000
      })}else if (error.status == 400){
        this.matSnackBar.open('La nuova password inserita non è valida', 'Undo', {
          duration: 3000
        })
      }}});
    this.matDialogRef.close();
  }

  cancel(): void{
    this.matDialogRef.close();
  }
}