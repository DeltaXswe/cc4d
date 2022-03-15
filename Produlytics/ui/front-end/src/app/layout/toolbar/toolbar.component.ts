import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { ModifyPwComponent } from 'src/app/main/modify-pw/modify-pw.component';
import { LoginService } from 'src/app/model/login/login.service';

@Component({
  selector: 'app-toolbar',
  templateUrl: './toolbar.component.html',
  styleUrls: ['./toolbar.component.css']
})
export class ToolbarComponent implements OnInit {

  isAdmin: boolean=true;

  constructor(private route: ActivatedRoute, public dialog: MatDialog, private loginService: LoginService) { }

    ngOnInit() {
    }

    logout(): void{
    }
    openPwDialog(): void{
      const dialogRef = this.dialog.open(ModifyPwComponent);
    }

    isLogged(): boolean{
      return this.loginService.isLogged();
    }
}
