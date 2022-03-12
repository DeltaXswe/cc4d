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

@NgModule({
  declarations: [
    AppComponent,
    SelectionComponent,
    MachineComponent,
    CharacteristicComponent,
    ChartComponent
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
    // Layout
    AppRoutingModule
  ],
  providers: [
    {
      provide: ChartAbstractService,
      useClass: environment.chartAbstractService
    }
  ],
  bootstrap: [
    AppComponent
  ],
})
export class AppModule {
}
