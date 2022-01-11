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

  ngOnInit(): void {
    this.getCharacteristics();
}

  characteristics: Characteristic[] = [];
  @Input() machine?: Machine;

  characteristicShow(characteristic: Characteristic): boolean{
    if(characteristic.macchina==this.machine?.ser){
      return true;
    }else{
      return false;
    }
  }
  

  characteristicOnSelect(characteristic: Characteristic){
    this.router.navigate(['grafico', characteristic.macchina, characteristic.nome]);
  }

  getCharacteristics():void{
    this.characteristicService.getCharacteristics().subscribe(characteristics => this.characteristics = characteristics);
  }
}
