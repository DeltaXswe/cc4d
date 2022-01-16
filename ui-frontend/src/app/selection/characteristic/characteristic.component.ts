import { Component, Input, OnInit } from '@angular/core';
import { CharacteristicService } from '../characteristic.service';
import { Characteristic } from './characteristic';
import { Machine } from '../machine/machine';
import { Router } from '@angular/router';

@Component({
  selector: 'app-characteristic',
  templateUrl: './characteristic.component.html',
  styleUrls: ['./characteristic.component.css']
})
export class CharacteristicComponent implements OnInit {

  constructor(private characteristicService: CharacteristicService, private router: Router) { }
  @Input() machine!: Machine;

  ngOnInit(): void {
    this.getCharacteristics(this.machine);
}

  characteristics: Characteristic[] = [];


  characteristicShow(characteristic: Characteristic): boolean{
    if(characteristic.machine==this.machine?.id){
      return true;
    }else{
      return false;
    }
  }
  
  characteristicOnSelect(characteristic: Characteristic){
    this.router.navigate(['chart', characteristic.machine, characteristic.name]);
  }

  getCharacteristics(machine: Machine):void{
    this.characteristicService.getCharacteristics(machine.id).subscribe(characteristics => this.characteristics = characteristics);
  }
}
