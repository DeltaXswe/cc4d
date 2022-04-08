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
    private dialog: MatDialog,
    private loginService: LoginAbstractService
    ) { }

    ngOnInit() {
    }

    openPwDialog(): void{
      const dialogRef = this.dialog.open(ModifyPwComponent);
    }

    isLogged(): boolean{
      return this.loginService.isLogged();
    }

    isAdmin(): boolean{  
      return this.loginService.isAdmin();
    } 

    getUsername(): string{
      return this.loginService.getUsername();
    }

    logout(): void{
      this.loginService.logout();
    }
}
