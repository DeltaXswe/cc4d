import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpErrorResponse} from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class ModifyPwService {

  constructor() { }

  confirmPassword(newPw: string, newPwRe: string) {
    if(newPw==newPwRe)
      return null;
    else
      return { 'mismatch': true }
  }

  modify(oldPw: string, newPw: string){

  }
}
