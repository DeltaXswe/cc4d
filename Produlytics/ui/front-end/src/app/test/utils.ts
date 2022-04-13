import {HttpClientTestingModule} from "@angular/common/http/testing";
import {RouterTestingModule} from "@angular/router/testing";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatSnackBarModule} from "@angular/material/snack-bar";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatIconModule} from "@angular/material/icon";
import {MatFormFieldModule} from "@angular/material/form-field";
import {MatDialogModule} from "@angular/material/dialog";
import {MatInputModule} from "@angular/material/input";
import {MatCheckboxModule} from "@angular/material/checkbox";
import {SaveAccountService} from "../model/admin-account/save-account.service";
import {SaveAccountAbstractService} from "../model/admin-account/save-account-abstract.service";
import {LoginService} from "../model/login/login.service";
import {LoginAbstractService} from "../model/login/login-abstract.service";
import {MatSlideToggleModule} from "@angular/material/slide-toggle";

export const testModules = [
  HttpClientTestingModule,
  RouterTestingModule,
  FormsModule,
  ReactiveFormsModule,
  BrowserAnimationsModule,
  MatSnackBarModule,
  MatToolbarModule,
  MatIconModule,
  MatFormFieldModule,
  MatDialogModule,
  MatInputModule,
  MatCheckboxModule,
  MatSlideToggleModule
];

// per i test d'integrazione
export const productionServices = [
  SaveAccountService,
  {
    provide: SaveAccountAbstractService,
    useExisting: SaveAccountService
  },
  LoginService,
  {
    provide: LoginAbstractService,
    useExisting: LoginService
  }
];

export async function sleep(ms: number): Promise<void> {
  return new Promise(resolve => {
    setTimeout(resolve, ms);
  });
}
