import { Injectable } from '@angular/core';
import { of } from 'rxjs';
import { ModifyPwAbstractService } from 'src/app/model/modify-pw/modify-pw-abstract.service';
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root'
})
export class FakeModifyPwService implements ModifyPwAbstractService{

  constructor(private http: HttpClient) { }

  modify(username: string, currentPassword: string, newPassword: string){
    return this.http.put(`/accounts/${username}/password`, {currentPassword, newPassword});
  }
}
