import { Component, OnInit } from '@angular/core';
import { MachineService } from '../machine.service';
import { Machine } from './machine';

@Component({
  selector: 'app-machine',
  templateUrl: './machine.component.html',
  styleUrls: ['./machine.component.css']
})
export class MachineComponent implements OnInit {

  constructor(private machineService: MachineService) { }

  ngOnInit(): void {
    this.getMachines();
  }

  machines: Machine[] = [];
  selectedMachine? : Machine;

  machineOnSelect(machine: Machine): void{
    this.selectedMachine = machine;
  }

  getMachines(): void{
    this.machineService.getMachines().subscribe(machines => this.machines = machines);
  }
}
