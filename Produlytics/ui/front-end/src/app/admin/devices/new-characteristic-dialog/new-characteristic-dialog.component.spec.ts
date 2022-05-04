import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NewCharacteristicDialogComponent } from './new-characteristic-dialog.component';
import {MockDialogRef, testModules} from "../../../test/utils";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {ComponentsModule} from "../../../components/components.module";

describe('NewCharacteristicDialogComponent', () => {
  let component: NewCharacteristicDialogComponent;
  let fixture: ComponentFixture<NewCharacteristicDialogComponent>;

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
            characteristics: []
          }
        }
      ]
    })
    .compileComponents();
    fixture = TestBed.createComponent(NewCharacteristicDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
