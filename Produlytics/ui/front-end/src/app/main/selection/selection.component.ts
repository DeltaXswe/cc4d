import {Component, EventEmitter, OnInit, Output, ViewChild} from '@angular/core';
import { FlatTreeControl } from "@angular/cdk/tree";
import {
  UnarchivedCharacteristicAbstractService
} from "../../model/characteristic/unarchived-characteristic-abstract.service";
import { UnarchivedDeviceAbstractService } from "../../model/device/unarchived-device-abstract.service";
import { SelectionNode } from './selection-data-source/selection-node';
import { SelectionDataSource } from './selection-data-source/selection.data-source';
import { CharacteristicNode } from './selection-data-source/characteristic-node';
import {NgbCarousel} from "@ng-bootstrap/ng-bootstrap";
import { MatDialog } from '@angular/material/dialog';
import { CarouselOptionsDialogComponent } from '../carousel-options-dialog/carousel-options-dialog/carousel-options-dialog.component';

@Component({
  selector: 'app-selection',
  templateUrl: './selection.component.html',
  styleUrls: ['./selection.component.css']
})
export class SelectionComponent implements OnInit {
  @Output()
  devicesChanged = new EventEmitter<CharacteristicNode[]>();

  @ViewChild('carousel') carousel!: NgbCarousel;

  carouselInterval: number = 5;
  isCarouselPaused: boolean = false;
  showCarousel: Boolean = false;
  treeControl: FlatTreeControl<SelectionNode>;
  dataSource: SelectionDataSource;

  checkedNodes: CharacteristicNode[] = [];

  private pendingSelection: CharacteristicNode[] = [];

  constructor(
    unarchivedDeviceService: UnarchivedDeviceAbstractService,
    unarchivedCharacteristicService: UnarchivedCharacteristicAbstractService,
    public dialog: MatDialog
  ) {
    this.treeControl = new FlatTreeControl<SelectionNode>(
      node => node.level,
      node => node.expandable
    );
    this.dataSource = new SelectionDataSource(
      this.treeControl,
      unarchivedDeviceService,
      unarchivedCharacteristicService
    );
  }

  get mobile() {
    return window.matchMedia('screen and (max-width: 1279px)').matches;
  }

  openCarouselDialog(): void {
    const dialogRef = this.dialog.open(CarouselOptionsDialogComponent, {
      data: {
        isCarouselOn: this.showCarousel,
        isCarouselPaused: this.isCarouselPaused,
        carouselInterval: this.carouselInterval
      }
    }
      );
    dialogRef.afterClosed().subscribe(data => {
      this.showCarousel = data.isCarouselOn;
      this.isCarouselPaused = data.isCarouselPaused;
      this.carouselInterval = data.carouselInterval;
      this.toggleCarouselPause();
    })
  }

  ngOnInit(): void { }

  toggleCarouselPause() {
    if (this.isCarouselPaused) {
      this.carousel.cycle();
    } else {
      this.carousel.pause();
    }
  }

  hasChildren(_index: number, node: SelectionNode): boolean {
    return node.expandable;
  }

  nodeIsChecked(node: CharacteristicNode): boolean {
    return this.pendingSelection.indexOf(node) >= 0;
  }

  checkNode(node: CharacteristicNode): void {
    this.pendingSelection.push(node);
  }

  uncheckNode(node: CharacteristicNode): void {
    this.pendingSelection.splice(this.pendingSelection.indexOf(node), 1);
  }

  notifyChange(): void {
    this.checkedNodes = [];
    setTimeout(() => {
      // l'abc dell'hack in angular - spesso il runtime di angular ha bisogno di "aspettare" un momento
      this.checkedNodes = this.pendingSelection.slice();
    });
    if (this.mobile) {

    }
  }
}
