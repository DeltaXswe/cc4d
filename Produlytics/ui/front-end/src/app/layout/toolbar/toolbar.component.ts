import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { ModifyPwComponent } from 'src/app/main/modify-pw/modify-pw.component';
import { LoginAbstractService } from 'src/app/model/login/login-abstract.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ToolbarComponent implements OnInit {

  constructor(
    public dialog: MatDialog,
    private loginService: LoginAbstractService
    ) { }

    ngOnInit() {
    }

    logout(): void{
      this.loginService.logout();
    }
    openPwDialog(): void{
      const dialogRef = this.dialog.open(ModifyPwComponent);
    }

    isLogged(): boolean{
      return this.loginService.isLogged();
    }

    getUsername(){
      if(localStorage.getItem('accessToken'))
        return this.loginService.getUsername();
      else
        return 'username';
    }

    isAdmin(){
      if(localStorage.getItem('accessToken'))
        return this.loginService.isAdmin();
      else
        return false;
    }
}
