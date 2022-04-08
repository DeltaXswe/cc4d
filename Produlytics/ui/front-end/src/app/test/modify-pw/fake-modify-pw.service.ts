import { Injectable } from '@angular/core';
import { ModifyPwAbstractService } from 'src/app/model/modify-pw/modify-pw-abstract.service';
import { users } from '../login/users';
import { of, throwError } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ModifyPwCommand } from 'src/app/model/modify-pw/modify-pw-command';
import { HttpErrorResponse } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FakeModifyPwService implements ModifyPwAbstractService{

  constructor(private matSnackBar: MatSnackBar) { }

  modifyPw(username: string, command: ModifyPwCommand){
    if(users.find(account => account.username === username) && 
      users.find(account => account.password === command.oldPassword)){
        users.find(account => account.username === username)!.password = command.newPassword;
        return of({});
    } else{
      const error = new HttpErrorResponse({ status: 401 });
      return throwError(() => error);
    }
  }
}
