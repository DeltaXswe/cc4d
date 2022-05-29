import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DatePickerDialogComponent } from './date-picker-dialog.component';
import {MockDialogRef, testModules} from "../../test/utils";
import {MatDialogRef} from "@angular/material/dialog";

describe('DatePickerDialogComponent', () => {
  let component: DatePickerDialogComponent;
  let fixture: ComponentFixture<DatePickerDialogComponent>;
  let mockDialogRef: MatDialogRef<any>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        testModules
      ],
      declarations: [ DatePickerDialogComponent ],
      providers: [
        {
          provide: MatDialogRef,
          useExisting: MockDialogRef
        },
        MockDialogRef
      ]

    })
      .compileComponents();
    fixture = TestBed.createComponent(DatePickerDialogComponent);
    component = fixture.componentInstance;
    mockDialogRef = TestBed.inject(MatDialogRef);
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('validità-campi-obbligatori', () => {
    let form = component.dateForm;
    expect(form.valid).toBeFalsy();
  });

  it('conferma', (done) => {
    let dataInizio = 'December 17, 1995 03:24:00';
    let dataFine = 'December 17, 1995 03:25:00';
    mockDialogRef.afterClosed()
      .subscribe({
        next: data => {
          expect(data)
            .withContext('date di inizio e fine')
            .toEqual({
              start: Date.parse(dataInizio) + 12*3600*1000, //Aggiungo le ore di default in UNIX Epoch
              end: Date.parse(dataFine) + 12*3600*1000});
        },
        error: done.fail,
        complete: done
      });
    component.dateForm.controls['start'].setValue(dataInizio);
    component.dateForm.controls['end'].setValue(dataFine);
    component.confirm();
  });

  it('annulla',  () => {
    mockDialogRef.afterClosed()
      .subscribe({
        next: value => {
          expect(value).toBeUndefined();
        }
      })
    component.cancel();
  })
});
