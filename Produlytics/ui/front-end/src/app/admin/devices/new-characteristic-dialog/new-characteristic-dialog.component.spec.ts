import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCharacteristicDialogComponent } from './new-characteristic-dialog.component';
import {MockDialogRef, testModules} from "../../../test/utils";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ComponentsModule} from "../../../components/components.module";
import {HttpClient} from "@angular/common/http";
import {HttpTestingController} from "@angular/common/http/testing";
import {testInsertCharacteristic} from "../new-device/new-device.component.spec";

describe('NewCharacteristicDialogComponent', () => {
  let component: NewCharacteristicDialogComponent;
  let fixture: ComponentFixture<NewCharacteristicDialogComponent>;
  let matDialogRef: MatDialogRef<any>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        ...testModules,
        ComponentsModule
      ],
      declarations: [ NewCharacteristicDialogComponent ],
      providers: [
        MockDialogRef,
        {
          provide: MatDialogRef,
          useExisting: MockDialogRef
        },
        {
          provide: MAT_DIALOG_DATA,
          useValue: {
            characteristics: [{
              name: 'Valvola'
            }]
          }
        }
      ]
    })
    .compileComponents();
    fixture = TestBed.createComponent(NewCharacteristicDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    matDialogRef = TestBed.inject(MatDialogRef);
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('rifiuta-nuova-caratteristica', () => {
    component.charForm.formGroup.setValue({
      name: 'Valvola',
      autoAdjust: true,
      sampleSize: 30,
      upperLimit: null,
      lowerLimit: null
    });
    component.confirm();
    expect(
      component.charForm.formGroup
        .get('name')?.hasError('duplicateCharacteristicName')
    ).toBeTrue();
  });

  it('annulla-nuova-caratteristica', (doneFn) => {
    matDialogRef.afterClosed().subscribe(value => {
      expect(value).toBeUndefined();
      doneFn();
    });
    component.cancel();
  });

  it('conferma-nuova-caratteristica', (doneFn) => {
    matDialogRef.afterClosed().subscribe(value => {
      expect(value).toEqual(testInsertCharacteristic);
      doneFn();
    });
    component.charForm.formGroup.setValue(testInsertCharacteristic);
    component.confirm();
  });
});

describe('NewCharacteristicDialogComponent Integration', () => {

  let component: NewCharacteristicDialogComponent;
  let fixture: ComponentFixture<NewCharacteristicDialogComponent>;
  let httpClient: HttpClient;
  let httpTestingController: HttpTestingController;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
        declarations: [NewCharacteristicDialogComponent],
        imports: [
          ...testModules,
          ComponentsModule
        ],
        providers: [
          MockDialogRef,
          {
            provide: MatDialogRef,
            useExisting: MockDialogRef
          },
          {
            provide: MAT_DIALOG_DATA,
            useValue: {
              characteristics: []
            }
          }
        ]
      })
      .compileComponents();

    fixture = TestBed.createComponent(NewCharacteristicDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    httpClient = TestBed.inject(HttpClient);
    httpTestingController = TestBed.inject(HttpTestingController);
  });

  it('new-characteristic-should-create', () => {
    expect(component).toBeTruthy();
  });
});

