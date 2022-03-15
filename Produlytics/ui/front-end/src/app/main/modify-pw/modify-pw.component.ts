import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';
import {MatDialogRef} from "@angular/material/dialog";
import { ModifyPwService } from 'src/app/model/modify-pw/modify-pw.service';
import {ViewEncapsulation} from '@angular/core';

@Component({
  selector: 'app-modify-pw',
  templateUrl: './modify-pw.component.html',
  styleUrls: ['./modify-pw.component.css'],
  encapsulation: ViewEncapsulation.None 
})
export class ModifyPwComponent implements OnInit {
  modifyPw: FormGroup;

  constructor(private matDialogRef: MatDialogRef<ModifyPwComponent>, formBuilder: FormBuilder, private modifyPwService: ModifyPwService) {
    this.modifyPw = formBuilder.group({
      oldPw: ['', Validators.required],
      newPw: ['', Validators.required, Validators.minLength(6)],
      newPwRe: ['', Validators.required],
  }, { validator: this.checkPasswords('newPw', 'newPwRe')})};

  ngOnInit(): void {
  }

  cancel(): void{
    this.matDialogRef.close();
  }
  
  checkPasswords(newPw: string, newPwRe:string){
    return (group: FormGroup) => {
      let passwordInput = group.controls[newPw],
          passwordConfirmationInput = group.controls[newPwRe];
      if (passwordInput.value !== passwordConfirmationInput.value) {
        return passwordConfirmationInput.setErrors({mismatch: true})
      }
      else {
          return passwordConfirmationInput.setErrors(null);
      }
    }
  }

  modify(){
    if (this.modifyPw.invalid) {
      return;
    }
    this.modifyPwService.modify(this.modifyPw.controls['oldPw'].value, this.modifyPw.controls['newPw'].value);
  }
}
