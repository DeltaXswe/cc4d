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
export class CharacteristicFormComponent implements OnInit {
  @Input()
  startingData: Characteristic | null = null;
  readonly formGroup: FormGroup;
  readonly duplicateNameBehavior = new BehaviorSubject<true | null>(null);

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

  requireData(): CharacteristicCreationCommand {
    const rawValue = this.formGroup.getRawValue();
    if (rawValue.autoAdjust) {
      rawValue.upperLimit = null;
      rawValue.lowerLimit = null;
    } else {
      rawValue.sampleSize = null;
    }
    return rawValue;
  }
}
