import { Component, OnInit } from '@angular/core';
import { Machine } from '../../model/machine';
import { MachineComponent } from './machine/machine.component';
import { Characteristic } from '../../model/characteristic';
import { CharacteristicComponent } from './characteristic/characteristic.component';

@Component({
  selector: 'app-selection',
  templateUrl: './selection.component.html',
  styleUrls: ['./selection.component.css']
})
export class SelectionComponent implements OnInit {
  machine: Machine | undefined;

  constructor() { }

  ngOnInit(): void {
  }

}
