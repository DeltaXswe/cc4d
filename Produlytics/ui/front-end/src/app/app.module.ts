import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {SelectionComponent} from "./main/selection/selection.component";
import {MachineComponent} from "./main/selection/machine/machine.component";
import {CharacteristicComponent} from "./main/selection/characteristic/characteristic.component";
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
import {HttpClientModule} from "@angular/common/http";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {MatTooltipModule} from "@angular/material/tooltip";
import {ChartAbstractService} from "./model/chart/chart-abstract.service";
import {environment} from "../environments/environment";
import {ComponentsModule} from "./components/components.module";
import {MatDialogModule} from "@angular/material/dialog";
import {AdminRoutingModule} from "./admin/admin-routing.module";
import {ListUnarchivedDevicesAbstractService} from "./model/public-device/list-unarchived-devices-abstract.service";
import {DeviceAbstractService} from "./model/admin-device/device-abstract.service";
import {AccountAbstractService} from "./model/admin-account/account-abstract.service";
import {SaveAccountService} from "./model/admin-account/save-account.service";
import {SaveAccountAbstractService} from "./model/admin-account/save-account-abstract.service";
import { ToolbarComponent } from './layout/toolbar/toolbar.component';
import { LoginComponent } from './main/login/login.component';

@NgModule({
  declarations: [
    AppComponent,
    SelectionComponent,
    MachineComponent,
    CharacteristicComponent,
    ChartComponent,
    ToolbarComponent,
    LoginComponent
  ],
  imports: [
    // angular
    BrowserModule,
    BrowserAnimationsModule,
    HttpClientModule,
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
    AppRoutingModule

  ],
  providers: [
    {
      provide: ChartAbstractService,
      useClass: environment.chartService
    },
    {
      provide: ListUnarchivedDevicesAbstractService,
      useClass: environment.unarchivedDeviceService
    },
    {
      provide: DeviceAbstractService,
      useClass: environment.deviceService
    },
    {
      provide: AccountAbstractService,
      useExisting: environment.accountService
    },
    {
      provide: SaveAccountAbstractService,
      useExisting: environment.saveAccountService
    }
  ],
  bootstrap: [
    AppComponent
  ],
})
export class AppModule {
}
