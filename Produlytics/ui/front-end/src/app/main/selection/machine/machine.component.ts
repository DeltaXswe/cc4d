import {Component, EventEmitter, OnInit, Output, ViewEncapsulation} from '@angular/core';
import { MachineService } from '../../../model/machine.service';
import { Machine } from '../../../model/machine';

@Component({
  selector: 'app-machine',
  templateUrl: './machine.component.html',
  styleUrls: ['./machine.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class MachineComponent implements OnInit {

  @Output()
  machineSelect = new EventEmitter<Machine>();

  constructor(private machineService: MachineService) { }

  ngOnInit(): void {
    this.getMachines();
  }

  machines: Machine[] = [];
  selectedMachine! : Machine;

  machineOnSelect(machine: Machine): void{
    this.selectedMachine = machine;
    this.machineSelect.emit(this.selectedMachine);
  }

  getMachines(): void{
    this.machineService.getMachines().subscribe(machines => this.machines = machines);
  }
}
