import { Component, OnInit } from '@angular/core';
import { Machine } from '../../model/device/machine';

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
