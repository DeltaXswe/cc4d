import { ViewEncapsulation } from '@angular/core';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import { MatDialogRef } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
/**
 * Questo component permette all'utente di selezionare gli estremi
 * temporali entro ai quali vuole vedere le rilevazioni.
 */
@Component({
  selector: 'app-date-picker-dialog',
  templateUrl: './date-picker-dialog.component.html',
  styleUrls: ['./date-picker-dialog.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class DatePickerDialogComponent implements OnInit {
  dateForm: FormGroup;
  startTime = {hour: 12, minute: 0, second: 0};
  endTime = {hour: 12, minute: 0, second: 0};

  constructor(private formBuilder: FormBuilder,
    private matDialogRef: MatDialogRef<DatePickerDialogComponent>,
    private matSnackbar: MatSnackBar) {
    this.dateForm = this.formBuilder.group({
      start: new FormControl('', Validators.required),
      end: new FormControl('', Validators.required)
    });
  }

  ngOnInit(): void {
  }

  /**
   * Prende i dati inseriti dall'utente e li converte da {@link Date} a UNIX EPOCH.
   * Li passa poi a {@link ChartComponent}.
   */
  confirm(): void{
    let data = {
      start: Date.parse(
        this.dateForm.getRawValue().start) + (
        (this.startTime.hour * 3600 + this.startTime.minute * 60 + this.startTime.second) * 1000),
      end: Date.parse(
        this.dateForm.getRawValue().end) + (
        (this.endTime.hour * 3600 + this.endTime.minute * 60 + this.endTime.second) * 1000)
    };
    if (data.start < data.end && data.end <= ((new Date).getTime())) {
      this.matDialogRef.close(data);
    } else {
      this.matSnackbar.open('Date inserite non valide', 'Ok');
      return;
    }

  }

  /**
   * Chiude la finestra di dialogo senza alcuna operazione aggiuntiva.
   */
  cancel(): void{
    this.matDialogRef.close();
  }
}
