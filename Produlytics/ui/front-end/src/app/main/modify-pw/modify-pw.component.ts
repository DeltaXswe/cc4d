import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators, FormBuilder, AbstractControl, AbstractControlOptions } from '@angular/forms';
import {MatDialogRef} from "@angular/material/dialog";
import {ViewEncapsulation} from '@angular/core';
import { LoginAbstractService } from 'src/app/model/login/login-abstract.service';
import { ModifyPwAbstractService } from 'src/app/model/modify-pw/modify-pw-abstract.service';

@Component({
  selector: 'app-modify-pw',
  templateUrl: './modify-pw.component.html',
  styleUrls: ['./modify-pw.component.css'],
  encapsulation: ViewEncapsulation.None 
})
export class ModifyPwComponent implements OnInit {
  modifyPw: FormGroup;

  constructor(private matDialogRef: MatDialogRef<ModifyPwComponent>,
    formBuilder: FormBuilder,
    private modifyPwService: ModifyPwAbstractService,
    private loginService: LoginAbstractService) {
    this.modifyPw = formBuilder.group({
      oldPw: ['', Validators.required],
      newPw: ['', Validators.required, Validators.minLength(6)],
      newPwRe: ['', Validators.required],
  }, { validator: this.checkPasswords('newPw', 'newPwRe')as AbstractControlOptions})};

  ngOnInit(): void {
  }

  cancel(): void{
    this.matDialogRef.close();
  }
  
  checkPasswords(newPw: string, newPwRe:string){
    return (controls: AbstractControl/* group: FormGroup */) => {
      if (controls.get(newPw) !== controls.get(newPwRe)) {
        controls.get(newPwRe)/* ?.setErrors({mismatch: true}) */;
        return ({mismatch:true});
      }
      else {
        controls.get(newPwRe);
        return null;
      }
    }
  }

  modify(){
    if (this.modifyPw.invalid) {
      return;
    }
    this.modifyPwService.modify(this.loginService.getUsername(),
      this.modifyPw.controls['oldPw'].value,
      this.modifyPw.controls['newPw'].value);
  }
}
