import {NgModule} from '@angular/core';
import {AppComponent} from './app.component';
import {SelectionComponent} from "./main/selection/selection.component";
import {ChartComponent} from "./main/chart/chart.component";
import {MatToolbarModule} from "@angular/material/toolbar";
import {MatTabsModule} from "@angular/material/tabs";
import {MatCardModule} from "@angular/material/card";
import {MatDividerModule} from "@angular/material/divider";
import {MatListModule} from "@angular/material/list";
import {MatIconModule} from "@angular/material/icon";
import {MatMenuModule} from '@angular/material/menu';
import {MatButtonModule} from '@angular/material/button';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatInputModule} from '@angular/material/input';
import {BrowserModule} from "@angular/platform-browser";
import {FlexLayoutModule, FlexModule} from "@angular/flex-layout";
import {AppRoutingModule} from "./app-routing.module";
import {HttpClientModule, HTTP_INTERCEPTORS} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatTooltipModule} from "@angular/material/tooltip";
import {ChartAbstractService} from "./model/chart/chart-abstract.service";
import {environment} from "../environments/environment";
import {MatDialogModule} from "@angular/material/dialog";
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {ListUnarchivedDevicesAbstractService} from "./model/public-device/list-unarchived-devices-abstract.service";
import {DeviceAbstractService} from "./model/admin-device/device-abstract.service";
import {AccountAbstractService} from "./model/admin-account/account-abstract.service";
import {SaveAccountAbstractService} from "./model/admin-account/save-account-abstract.service";
import { ToolbarComponent } from './layout/toolbar/toolbar.component';
import { LoginComponent } from './main/login/login.component';
import {ComponentsModule} from "./components/components.module";
import {NewDeviceAbstractService} from "./model/admin-device/new/new-device-abstract.service";
import { FormsModule }   from '@angular/forms';
import { ReactiveFormsModule }   from '@angular/forms';
import { ModifyPwComponent } from './main/modify-pw/modify-pw.component';
import {FindDeviceAbstractService} from "./model/admin-device/find-detail/find-device-abstract.service";
import {CharacteristicAbstractService} from "./model/admin-device/characteristic/characteristic-abstract.service";
import { LoginAbstractService } from './model/login/login-abstract.service';
import {UpdateDeviceAbstractService} from "./model/admin-device/update/update-device-abstract.service";
import {
  UpdateCharacteristicAbstractService
} from "./model/admin-device/characteristic/update-characteristic-abstract.service";
import { ModifyPwAbstractService } from './model/modify-pw/modify-pw-abstract.service';
import { CookieService } from 'ngx-cookie-service';
import { DeviceSelectionComponent } from './main/device-selection/device-selection.component';
import {MatSidenavModule} from "@angular/material/sidenav";
import {MatTreeModule} from "@angular/material/tree";
import {MatDatepickerModule} from '@angular/material/datepicker';
import {MatProgressBarModule} from "@angular/material/progress-bar";
import {
  UnarchivedCharacteristicAbstractService
} from "./model/characteristic/unarchived-characteristic-abstract.service";
import {UnarchivedDeviceAbstractService} from "./model/device/unarchived-device-abstract.service";
import {MatGridListModule} from '@angular/material/grid-list';
import { MatNativeDateModule, MAT_DATE_LOCALE } from '@angular/material/core';
import {MatProgressSpinnerModule} from "@angular/material/progress-spinner";
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { DatePickerDialogComponent } from './main/date-picker-dialog/date-picker-dialog.component';
import { XhrInterceptor } from './model/http-interceptor/http-interceptor.service';

@NgModule({
  declarations: [
    AppComponent,
    SelectionComponent,
    ChartComponent,
    ToolbarComponent,
    LoginComponent,
    ModifyPwComponent,
    DeviceSelectionComponent,
    DatePickerDialogComponent
  ],
  imports: [
    // angular
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
    MatDatepickerModule,
    MatNativeDateModule,
    // flex style
    FlexModule,
    FlexLayoutModule,
    // material
    MatToolbarModule,
    MatTabsModule,
    MatCardModule,
    MatDividerModule,
    MatListModule,
    MatIconModule,
    MatTooltipModule,
    MatDialogModule,
    MatMenuModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatCheckboxModule,
    // routing
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    MatSnackBarModule,
    // other
    ComponentsModule,
    MatSidenavModule,
    MatTreeModule,
    MatProgressBarModule,
    MatProgressSpinnerModule,
    MatGridListModule,
    NgbModule
  ],
  providers: [
    {
      provide: MAT_DATE_LOCALE, useValue: 'en-GB'
    },
    { 
      provide: HTTP_INTERCEPTORS, 
      useClass: XhrInterceptor, 
      multi: true },
    {
      provide: ChartAbstractService,
      useExisting: environment.chartService
    },
    {
      provide: ListUnarchivedDevicesAbstractService,
      useExisting: environment.unarchivedDeviceService
    },
    {
      provide: DeviceAbstractService,
      useExisting: environment.deviceService
    },
    {
      provide: AccountAbstractService,
      useExisting: environment.accountService
    },
    {
      provide: SaveAccountAbstractService,
      useExisting: environment.saveAccountService
    },
    {
      provide: NewDeviceAbstractService,
      useExisting: environment.newDeviceService
    },
    {
      provide: LoginAbstractService,
      useExisting: environment.loginService
    },
    {
      provide: FindDeviceAbstractService,
      useExisting: environment.findDeviceService
    },
    {
      provide: CharacteristicAbstractService,
      useExisting: environment.characteristicService
    },
    {
      provide: UpdateDeviceAbstractService,
      useExisting: environment.updateDeviceService
    },
    {
      provide: UpdateCharacteristicAbstractService,
      useExisting: environment.updateCharacteristicService
    },
    {
      provide: ModifyPwAbstractService,
      useExisting: environment.modifyPwService
    },
    {
      provide: UnarchivedCharacteristicAbstractService,
      useExisting: environment.unarchivedCharacteristicService
    },
    {
      provide: UnarchivedDeviceAbstractService,
      useExisting: environment.unarchivedDeviceService
    },
    CookieService
  ],
  bootstrap: [
    AppComponent
  ],
})
export class AppModule {
}
