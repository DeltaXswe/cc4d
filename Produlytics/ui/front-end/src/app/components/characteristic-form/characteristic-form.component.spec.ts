import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CharacteristicFormComponent } from './characteristic-form.component';
import {testModules} from "../../test/utils";

describe('CharacteristicFormComponent', () => {
  let component: CharacteristicFormComponent;
  let fixture: ComponentFixture<CharacteristicFormComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: testModules,
      declarations: [ CharacteristicFormComponent ]
    })
    .compileComponents();
    fixture = TestBed.createComponent(CharacteristicFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('richiedi-dati-caratteristica-autoadjust', () => {
    component.startingData = {
      id: 1,
      archived: false,
      name: 'Pressione gomme',
      autoAdjust: true,
      upperLimit: 13,
      lowerLimit: 11,
      sampleSize: 40
    };
    component.ngOnInit();
    const command = component.requireData();
    expect(command.name).toEqual('Pressione gomme');
    expect(command.autoAdjust).toBeTrue();
    expect(command.sampleSize).toEqual(40);
    expect(command.lowerLimit).toBeNull();
    expect(command.upperLimit).toBeNull();
  });

  it('richiedi-dati-caratteristica-fixed', () => {
    component.formGroup.setValue({
      name: 'Pressione gomme',
      autoAdjust: false,
      upperLimit: 13,
      lowerLimit: 11,
      sampleSize: 40
    });
    const command = component.requireData();
    expect(command.name).toEqual('Pressione gomme');
    expect(command.autoAdjust).toBeFalse();
    expect(command.sampleSize).toBeNull();
    expect(command.lowerLimit).toEqual(11);
    expect(command.upperLimit).toEqual(13);
  });
});
