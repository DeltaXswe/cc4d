import { Component, OnInit } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
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
export class ModifyPwComponent implements OnInit {
  modifyPw: FormGroup;

  constructor(private matDialogRef: MatDialogRef<ModifyPwComponent>,
    private formBuilder: FormBuilder,
    private modifyPwService: ModifyPwAbstractService,
    private loginService: LoginAbstractService) {
      this.modifyPw = this.formBuilder.group({
        oldPassword: ['', Validators.required],
        newPassword: ['', [Validators.required, Validators.minLength(6)]],
        newPasswordRe: ['', Validators.required]
      }, { validator: this.checkPasswords('oldPassword', 'newPassword', 'newPasswordRe') })}; 

  
  ngOnInit(): void {
  }

  cancel(): void{
    this.matDialogRef.close();
  }
  
  checkPasswords(oldPassword: string, newPassword: string, newPasswordRe: string){
    return (group: FormGroup) => {
      let oldPasswordInput = group.controls[oldPassword], 
          passwordInput = group.controls[newPassword],
          passwordConfirmationInput = group.controls[newPasswordRe];
      if (passwordInput.value !== passwordConfirmationInput.value) {
        return passwordConfirmationInput.setErrors({mismatch: true})
      } else if(oldPasswordInput.value == passwordInput.value) {
        return passwordInput.setErrors({mustBeDifferent: true})
      }
      else {
          return passwordConfirmationInput.setErrors(null);
      }
    }
  }

  modify(){
    const rawValue = this.modifyPw.getRawValue();
    const command: ModifyPwCommand = {
      oldPassword: rawValue.oldPassword,
      newPassword: rawValue.newPassword,
    }  
    if (this.modifyPw.invalid) {
      return;
    }
    this.modifyPwService.modify(this.loginService.getUsername(), command);
    this.matDialogRef.close();
  }
}
