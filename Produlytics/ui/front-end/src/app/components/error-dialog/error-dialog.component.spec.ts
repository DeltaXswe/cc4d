import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ErrorDialogComponent } from './error-dialog.component';
import {MockDialogRef, testModules} from "../../test/utils";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {expect} from "@angular/flex-layout/_private-utils/testing";

describe('ErrorDialogComponent', () => {
  let component: ErrorDialogComponent;
  let fixture: ComponentFixture<ErrorDialogComponent>;
  let dialogRef: MatDialogRef<any>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: testModules,
      declarations: [ ErrorDialogComponent ],
      providers: [
        MockDialogRef,
        {
          provide: MatDialogRef,
          useExisting: MockDialogRef
        },
        {
          provide: MAT_DIALOG_DATA,
          useValue: {
            message: 'Test'
          }
        }
      ]
    })
    .compileComponents();
    fixture = TestBed.createComponent(ErrorDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    dialogRef = TestBed.inject(MatDialogRef);
  });

  it('should create', (doneFn) => {
    expect(component).toBeTruthy();
    dialogRef.afterClosed().subscribe(val => {
      expect(val).toBeUndefined();
      doneFn();
    });
    component.close();
  });
});
