import {Component, Input, OnInit, ViewEncapsulation} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {Characteristic} from "../../model/admin-device/characteristic/characteristic";
import {BehaviorSubject, startWith} from "rxjs";
import {CharacteristicCreationCommand} from "../../model/admin-device/new/characteristic-creation-command";

@Component({
  selector: 'app-characteristic-form',
  templateUrl: './characteristic-form.component.html',
  styleUrls: ['./characteristic-form.component.css'],
  encapsulation: ViewEncapsulation.None
})
/**
 * Questa classe incapsula la logica di inserimento o modifica di una caratteristica.
 */
export class CharacteristicFormComponent implements OnInit {

  @Input() startingData: Characteristic | null = null;

  formGroup: FormGroup;

  duplicateNameBehavior = new BehaviorSubject<true | null>(null);

  constructor(
    formBuilder: FormBuilder
  ) {
    this.formGroup = formBuilder.group({
      name: new FormControl('', Validators.required),
      autoAdjust: new FormControl(false),
      upperLimit: new FormControl(''),
      lowerLimit: new FormControl(''),
      sampleSize: new FormControl('')
    });
  }

  /**
   * Ereditato da {@link OnInit}. Configura la logica di validazioni dei dati del {@link FormGroup}. Inoltre,
   * inizializza il form con i dati iniziali se sono presenti.
   */
  ngOnInit(): void {
    if (this.startingData) {
      this.formGroup.setValue({
        name: this.startingData.name,
        autoAdjust: this.startingData.autoAdjust,
        upperLimit: this.startingData.upperLimit,
        lowerLimit: this.startingData.lowerLimit,
        sampleSize: this.startingData.sampleSize
      });
    }
    this.formGroup.get('autoAdjust')?.valueChanges
      .pipe(startWith(this.startingData?.autoAdjust || false))
      .subscribe(selected => {
        if (selected) {
          this.formGroup.get('upperLimit')?.removeValidators(Validators.required);
          this.formGroup.get('upperLimit')?.updateValueAndValidity();
          this.formGroup.get('lowerLimit')?.removeValidators(Validators.required);
          this.formGroup.get('lowerLimit')?.updateValueAndValidity();
        } else {
          this.formGroup.get('upperLimit')?.setValidators(Validators.required);
          this.formGroup.get('upperLimit')?.updateValueAndValidity();
          this.formGroup.get('lowerLimit')?.setValidators(Validators.required);
          this.formGroup.get('lowerLimit')?.updateValueAndValidity();
        }
      });
    this.duplicateNameBehavior.subscribe(isError => {
      this.formGroup.get('name')?.setErrors({
        duplicateCharacteristicName: isError
      });
      this.formGroup.updateValueAndValidity();
    });
  }

  /**
   * Questo metodo permette di ottenere i dati del {@link FormGroup} sotto forma di {@link CharacteristicCreationCommand}.
   */
  requireData(): CharacteristicCreationCommand {
    const rawValue = this.formGroup.getRawValue();
    if (rawValue.sampleSize === '') {
      rawValue.sampleSize = null;
    }
    if (rawValue.upperLimit === '') {
      rawValue.upperLimit = null;
    }
    if (rawValue.lowerLimit === '') {
      rawValue.lowerLimit = null;
    }
    return rawValue;
  }
}
