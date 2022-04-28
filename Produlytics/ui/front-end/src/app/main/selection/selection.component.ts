import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import { CharacteristicNode } from '../device-selection/selection-data-source/characteristic-node';
/**
 * Component la cui view racchiude la selezione dei grafici e la loro rappresentazione
 */
@Component({
  selector: 'app-selection',
  templateUrl: './selection.component.html',
  styleUrls: ['./selection.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class SelectionComponent implements OnInit {

  characteristics: CharacteristicNode[] = [];
  constructor() { }

  ngOnInit(): void {
  }

  /**
   * Notifica {@link ChartComponent} che l'utente ha premuto il tasto di conferma
   * dell'albero di selezione passandogli l'elenco di caratteristiche selezionate.
   * @param characteristics un elenco contentente le informazioni di caratteristiche e relative macchine
   * selezionate dall'utente
   */
  onSubmit(characteristics: CharacteristicNode[]){
    this.characteristics = characteristics.slice();
  }
}
