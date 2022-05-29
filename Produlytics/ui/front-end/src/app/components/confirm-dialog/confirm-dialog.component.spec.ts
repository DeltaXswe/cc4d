import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ConfirmDialogComponent } from './confirm-dialog.component';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {MockDialogRef, testModules} from "../../test/utils";
import {expect} from "@angular/flex-layout/_private-utils/testing";

describe('ConfirmDialogComponent', () => {
  let component: ConfirmDialogComponent;
  let fixture: ComponentFixture<ConfirmDialogComponent>;
  let dialogRef: MatDialogRef<any>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: testModules,
      declarations: [ ConfirmDialogComponent ],
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
    fixture = TestBed.createComponent(ConfirmDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
    dialogRef = TestBed.inject(MatDialogRef);
  });

  it('should create', (doneFn) => {
    expect(component).toBeTruthy();
    dialogRef.afterClosed().subscribe(val => {
      expect(val).toBeFalse();
      doneFn();
    });
    component.cancel();
  });

  it('should-be-true', (doneFn) => {
    dialogRef.afterClosed().subscribe(val => {
      expect(val).toBeTrue();
      doneFn();
    });
    component.confirm();
  });
});
