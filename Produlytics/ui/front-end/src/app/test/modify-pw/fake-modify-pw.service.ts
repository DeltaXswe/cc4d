import { Injectable } from '@angular/core';
import { ModifyPwAbstractService } from 'src/app/model/modify-pw/modify-pw-abstract.service';
import { users } from '../login/users';
import { throwError, of } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class FakeModifyPwService implements ModifyPwAbstractService{

  constructor(private matSnackBar: MatSnackBar) { }

  modify(username: string, currentPassword: string, newPassword: string){
    if(users.find(account => account.username === username) && 
      users.find(account => account.password === currentPassword)){
        users.find(account => account.username === username)!.password = newPassword;
        this.matSnackBar.open('Password cambiata con successo', 'Undo', {
          duration: 3000
        });
        return of({});
    } else{
      this.matSnackBar.open('La password corrente è errata', 'Undo', {
        duration: 3000
      });
      return throwError('la password non è corretta');
    }
  }
}
