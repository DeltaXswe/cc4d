import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CarouselOptionsDialogComponent } from './carousel-options-dialog.component';
import {MockDialogAlwaysConfirm, MockDialogRef, testModules} from "../../../test/utils";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {CarouselOptions} from "./carousel-options.types";

describe('CarouselOptionsDialogComponent', () => {
  let component: CarouselOptionsDialogComponent;
  let fixture: ComponentFixture<CarouselOptionsDialogComponent>;
  let mockDialogRef: MockDialogRef<CarouselOptions>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: testModules,
      providers: [
        MockDialogRef,
        {
          provide: MatDialogRef,
          useExisting: MockDialogRef
        },
        {
          provide: MAT_DIALOG_DATA,
          useFactory: () => ({
            carouselInterval: 5,
            isCarouselCycling: false,
            isCarouselOn: false,
          })
        },
        MockDialogAlwaysConfirm,
        {
          provide: MatDialog,
          useExisting: MockDialogAlwaysConfirm
        }
      ],
      declarations: [ CarouselOptionsDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(CarouselOptionsDialogComponent);
    component = fixture.componentInstance;
    mockDialogRef = TestBed.inject(MockDialogRef);
    fixture.detectChanges();
  });

  it('should-create', () => {
    expect(component).toBeTruthy();
  });

  it('cancel-dialog', () => {
    mockDialogRef.afterClosed().subscribe(value => {
      expect(value).toBeUndefined();
    });
    component.cancel();
  });

  it('confirm-options', () => {
    mockDialogRef.afterClosed().subscribe((value: CarouselOptions) => {
      expect(value.isCarouselCycling).toBeFalse();
      expect(value.carouselInterval).toEqual(5);
      expect(value.isCarouselOn).toBeFalse();
    });
    component.confirm();
  })
});
