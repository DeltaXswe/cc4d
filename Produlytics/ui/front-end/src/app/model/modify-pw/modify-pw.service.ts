import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpErrorResponse} from '@angular/common/http';
import { Router, ActivatedRoute } from '@angular/router';
import { FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';
import { ModifyPwAbstractService } from './modify-pw-abstract.service';

@Injectable({
  providedIn: 'root'
})
export class ModifyPwService implements ModifyPwAbstractService{

  constructor(private http: HttpClient) { }

  modify(username: string, currentPassword: string, newPassword: string){
    return this.http.put(`/accounts/${username}/password`, {currentPassword, newPassword});
  }
}
