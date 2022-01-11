import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { ChartComponent } from './chart/chart.component';
import { SelectionComponent } from './selection/selection.component';
import { MachineComponent } from './selection/machine/machine.component';
import { CharacteristicComponent } from './selection/characteristic/characteristic.component';

@NgModule({
  declarations: [
    AppComponent,
    ChartComponent,
    SelectionComponent,
    MachineComponent,
    CharacteristicComponent,
  ],
  imports: [AppRoutingModule, BrowserModule, HttpClientModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
