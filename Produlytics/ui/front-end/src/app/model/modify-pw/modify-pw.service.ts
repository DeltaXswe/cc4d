import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders, HttpErrorResponse} from '@angular/common/http';
import { ModifyPwAbstractService } from './modify-pw-abstract.service';
import { ModifyPwCommand } from './modify-pw-command';

@Injectable({
  providedIn: 'root'
})
export class ModifyPwService implements ModifyPwAbstractService{

  constructor(private http: HttpClient) { }

  modify(username: string, command: ModifyPwCommand){
    return this.http.put(`/accounts/${username}/password`, command);
  }
}
