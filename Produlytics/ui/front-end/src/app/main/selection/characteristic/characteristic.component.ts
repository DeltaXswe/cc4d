import {Component, EventEmitter, Input, OnChanges, Output, SimpleChanges} from '@angular/core';
import { CharacteristicService } from '../../../model/characteristic/characteristic.service';
import { Characteristic } from '../../../model/characteristic/characteristic';
import { UnarchivedDeviceInfo } from '../../../model/public-device/unarchived_device_info';
import { Router } from '@angular/router';

@Component({
  selector: 'app-characteristic',
  templateUrl: './characteristic.component.html',
  styleUrls: ['./characteristic.component.css']
})
export class CharacteristicComponent implements OnChanges {

  @Output() back = new EventEmitter<void>();

  @Input() machine?: UnarchivedDeviceInfo;

  constructor(private characteristicService: CharacteristicService, private router: Router) { }

  ngOnChanges(_changes: SimpleChanges): void {
    this.getCharacteristics(this.machine);
  }

  characteristics: Characteristic[] = [];

  characteristicOnSelect(characteristic: Characteristic){
    this.router.navigate(['chart', this.machine?.id, characteristic.id]);
  }

  getCharacteristics(machine?: UnarchivedDeviceInfo):void{
    if(machine) {
      this
        .characteristicService
        .getCharacteristics(machine.id)
        .subscribe(characteristics => this.characteristics = characteristics);
    } else {
      this.characteristics = [];
    }
  }
}
