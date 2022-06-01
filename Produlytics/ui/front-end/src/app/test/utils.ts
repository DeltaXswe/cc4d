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
import {BehaviorSubject, Observable, Subject} from "rxjs";
import {MatCardModule} from "@angular/material/card";
import {MatPaginatorModule} from "@angular/material/paginator";
import {MatTableModule} from "@angular/material/table";
import {Routes} from "@angular/router";
import {DeviceDetailComponent} from "../admin/devices/device-detail/device-detail.component";
import {NewDeviceComponent} from "../admin/devices/new-device/new-device.component";
import {MatDividerModule} from "@angular/material/divider";
import {MatListModule} from "@angular/material/list";
import {MatChipsModule} from "@angular/material/chips";
import { MatMenu, MatMenuModule } from "@angular/material/menu";
import {MatDatepickerModule} from "@angular/material/datepicker";
import {MatNativeDateModule} from "@angular/material/core";
import {LoginComponent} from "../main/login/login.component";
import {SelectionComponent} from "../main/selection/selection.component";
import {AuthenticatedUserGuard} from "../guards/authenticated-user-guard";
import {AdminGuard} from "../guards/admin-guard";
import {LoginGuard} from "../guards/login-guard";
import {ClipboardModule} from "@angular/cdk/clipboard";
import {MatTreeModule} from "@angular/material/tree";
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatProgressBarModule} from "@angular/material/progress-bar";

const routes: Routes = [
  {
    path: 'gestione-macchine',
    children: [
      {
        path: 'nuova',
        component: NewDeviceComponent
      },
      {
        path: ':id',
        component: DeviceDetailComponent
      }
    ]
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'dashboard',
    component: SelectionComponent
  }
];

export const testModules = [
  HttpClientTestingModule,
  ClipboardModule,
  RouterTestingModule.withRoutes(routes),
  FormsModule,
  ReactiveFormsModule,
  BrowserAnimationsModule,
  MatSnackBarModule,
  MatToolbarModule,
  MatIconModule,
  MatFormFieldModule,
  MatInputModule,
  MatCheckboxModule,
  MatSlideToggleModule,
  MatDialogModule,
  MatCardModule,
  MatPaginatorModule,
  MatDatepickerModule,
  MatNativeDateModule,
  MatTableModule,
  MatDividerModule,
  MatListModule,
  MatMenuModule,
  MatChipsModule,
  MatTreeModule,
  MatSidenavModule,
  MatProgressBarModule
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

export class MockDialogRef<T> {
  private readonly _afterClosed = new Subject<T | undefined>();
  public close(value?: T) {
    this._afterClosed.next(value);
    this._afterClosed.complete();
  }

  public afterClosed(): Observable<any> {
    return this._afterClosed;
  }
}

export class MockDialog {
  open(_?: any, options?: any) {
    return new MockDialogRef();
  }
}

export class MockDialogRefAlwaysConfirm {
  private readonly _afterClosed = new BehaviorSubject<boolean>(false);

  constructor() {
    this.close(true);
  }

  public close(value: boolean = false) {
    this._afterClosed.next(value);
  }

  public afterClosed(): Observable<any> {
    return this._afterClosed;
  }
}

export class MockDialogAlwaysConfirm {
  open(_?: any, options?: any) {
    return new MockDialogRefAlwaysConfirm();
  }
}

// a quanto pare la snackbar crea dei tick aggiuntivi
export class MockSnack {
  open(message: string, action?: string, config?: any): any {
    return undefined;
  }
}
