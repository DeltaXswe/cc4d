import {Component, EventEmitter, OnInit, Output, ViewEncapsulation} from '@angular/core';
import { UnarchivedDeviceService } from '../../../model/public-device/unarchived-device.service';
import { UnarchivedDeviceInfo } from '../../../model/public-device/unarchived_device_info';

@Component({
  selector: 'app-machine',
  templateUrl: './machine.component.html',
  styleUrls: ['./machine.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class MachineComponent implements OnInit {

  @Output()
  machineSelect = new EventEmitter<UnarchivedDeviceInfo>();

  constructor(private machineService: UnarchivedDeviceService) { }

  ngOnInit(): void {
    this.getMachines();
  }

  machines: UnarchivedDeviceInfo[] = [];
  selectedMachine! : UnarchivedDeviceInfo;

  machineOnSelect(machine: UnarchivedDeviceInfo): void{
    this.selectedMachine = machine;
    this.machineSelect.emit(this.selectedMachine);
  }

  getMachines(): void{
    this.machineService.getUnarchivedDevices().subscribe(machines => this.machines = machines);
  }
}
