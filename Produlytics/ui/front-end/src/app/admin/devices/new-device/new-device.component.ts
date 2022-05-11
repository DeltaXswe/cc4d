import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {CharacteristicCreationCommand} from "../../../model/admin-device/new/characteristic-creation-command";
import {MatDialog} from "@angular/material/dialog";
import {NewCharacteristicDialogComponent} from "../new-characteristic-dialog/new-characteristic-dialog.component";
import {NewDeviceAbstractService} from "../../../model/admin-device/new/new-device-abstract.service";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {DeviceCreationCommand} from "../../../model/admin-device/new/device-creation-command";
import {NotificationService} from "../../../utils/notification.service";

@Component({
  selector: 'app-new-device',
  templateUrl: './new-device.component.html',
  styleUrls: ['./new-device.component.css']
})
/**
 * Questa pagina permette di creare una nuova macchina.
 */
export class NewDeviceComponent implements OnInit {

  formGroup: FormGroup;

  characteristics: CharacteristicCreationCommand[] = [];
  readonly displayedColumns = [
    'name',
    'limits',
    'delete'
  ];

  constructor(
    private matDialog: MatDialog,
    private newDeviceService: NewDeviceAbstractService,
    private router: Router,
    private notificationService: NotificationService,
    formBuilder: FormBuilder
  ) {
    this.formGroup = formBuilder.group({
      name: new FormControl('', Validators.required)
    });
  }

  /**
   * Ereditato da {@link OnInit}. Inizializza la logica di validazione del form.
   */
  ngOnInit(): void {
    this.initForm();
  }

  /**
   * Apre una finestra di dialogo (classe {@link NewCharacteristicDialogComponent}) che permette di aggiungere una
   * caratteristica alla macchina in creazione. Alla chiusura della finestra di dialogo, se l'operazione non era
   * stata annullata, viene aggiunta la caratteristica all'elenco.
   */
  openNewCharacteristicDialog(): void {
    const dialogRef = this.matDialog.open(NewCharacteristicDialogComponent, {
      data: {
        characteristics: this.characteristics
      }
    });
    dialogRef.afterClosed().subscribe(value => {
      if (value) {
        this.characteristics = this.characteristics.concat(value);
      }
    });
  }

  /**
   * Tenta l'inserimento della macchina, interfacciandosi con un servizio che implementa {@link NewDeviceAbstractService}.
   * Se l'inserimento va a buon fine, l'utente visualizza tramite una {@link MatSnackBar} un messaggio. Altrimenti,
   * viene notificato al form dei dati di mostrare una spiegazione dell'errore.
   */
  insertDevice(): void {
    const device: DeviceCreationCommand = {
      name: this.formGroup.getRawValue().name,
      characteristics: this.characteristics
    }
    this.newDeviceService.insertDevice(device).subscribe({
      next: value => {
        this.router.navigate(['gestione-macchine', value.id]);
        this.notificationService.notify('Macchina creata con successo');
      },
      error: err => {
        if (err.errorCode === 'duplicateDeviceName') {
          this.formGroup.get('name')?.setErrors({
            duplicateDeviceName: true
          });
          this.formGroup.updateValueAndValidity();
        } else {
          this.notificationService.unexpectedError(`Errore inaspettato: ${JSON.stringify(err)}`);
        }
      }
    })
  }

  /**
   * Inizializza la logica di validazione del campo del nome della macchina.
   * @private che contiene solo un campo hihihi
   *
   */
  private initForm(): void {
    const nameField = this.formGroup.get('name');
    if (nameField) {
      nameField.valueChanges.subscribe(() => {
        if (nameField.hasError('duplicateDeviceName')) {
          nameField.setErrors({ duplicateDeviceName: null });
        }
      });
    }
  }

  removeCharacteristic(row: CharacteristicCreationCommand) {
    this.characteristics = this.characteristics.filter(element => element !== row);
  }
}
