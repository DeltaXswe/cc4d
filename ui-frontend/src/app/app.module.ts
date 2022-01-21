import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { ChartComponent } from './chart/chart.component';
import { SelectionComponent } from './selection/selection.component';
import { MachineComponent } from './selection/machine/machine.component';
import { CharacteristicComponent } from './selection/characteristic/characteristic.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatButtonModule} from "@angular/material/button";
import {MatListModule} from "@angular/material/list";
import {MatToolbarModule} from "@angular/material/toolbar";
import {FlexModule} from "@angular/flex-layout";
import {MatCardModule} from "@angular/material/card";

@NgModule({
  declarations: [
    AppComponent,
    ChartComponent,
    SelectionComponent,
    MachineComponent,
    CharacteristicComponent,
  ],
  imports: [AppRoutingModule, BrowserModule, HttpClientModule, BrowserAnimationsModule, MatButtonModule, MatListModule, MatToolbarModule, FlexModule, MatCardModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
