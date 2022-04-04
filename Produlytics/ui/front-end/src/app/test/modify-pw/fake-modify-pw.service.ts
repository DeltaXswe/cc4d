import { Injectable } from '@angular/core';
import { ModifyPwAbstractService } from 'src/app/model/modify-pw/modify-pw-abstract.service';
import { users } from '../login/users';
import { of } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ModifyPwCommand } from 'src/app/model/modify-pw/modify-pw-command';

@Injectable({
  providedIn: 'root'
})
export class FakeModifyPwService implements ModifyPwAbstractService{

  constructor(private matSnackBar: MatSnackBar) { }

  modify(username: string, command: ModifyPwCommand){
    if(users.find(account => account.username === username) && 
      users.find(account => account.password === command.oldPassword)){
        users.find(account => account.username === username)!.password = command.newPassword;
        this.matSnackBar.open('Password cambiata con successo', 'Undo', {
          duration: 3000
        });
        return of({});
    } else{
      this.matSnackBar.open('La password corrente è errata', 'Undo', {
        duration: 3000
      });
      return of({});
    }
  }
}
