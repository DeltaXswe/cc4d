import {Component, OnInit, ViewEncapsulation} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Device} from "../../../model/admin-device/device";
import {CharacteristicsDataSource} from "./characteristics.data-source";
import {
  CharacteristicAbstractService
} from "../../../model/admin-device/characteristic/characteristic-abstract.service";
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {UpdateDeviceAbstractService} from "../../../model/admin-device/update/update-device-abstract.service";
import {MatDialog} from "@angular/material/dialog";
import {NewCharacteristicDialogComponent} from "../new-characteristic-dialog/new-characteristic-dialog.component";
import {Characteristic} from "../../../model/admin-device/characteristic/characteristic";
import {CharacteristicCreationCommand} from "../../../model/admin-device/new/characteristic-creation-command";
import {
  UpdateCharacteristicDialogComponent
} from "../update-characteristic-dialog/update-characteristic-dialog.component";
import {MatSnackBar} from "@angular/material/snack-bar";
import {NotificationService} from "../../../utils/notification.service";
import {StandardError} from "../../../../lib/standard-error";
import {environment} from "../../../../environments/environment";

@Component({
  selector: 'app-device-detail',
  templateUrl: './device-detail.component.html',
  styleUrls: ['./device-detail.component.css'],
  encapsulation: ViewEncapsulation.None
})
/**
 * Questa classe rappresenta una singola macchina e ne gestisce le modifiche.
 */
export class DeviceDetailComponent implements OnInit {

  characteristics = new CharacteristicsDataSource();
  deviceNameControl: FormControl;
  device: Device;

  readonly displayedColumns = ['name', 'edit', 'status'];

  constructor(
    private characteristicService: CharacteristicAbstractService,
    private updateDeviceService: UpdateDeviceAbstractService,
    private matDialog: MatDialog,
    private activatedRoute: ActivatedRoute,
    private notificationService: NotificationService
  ) {
    this.device = activatedRoute.snapshot.data['device'];
    this.deviceNameControl = new FormControl(this.device.name, Validators.required);
  }

  /**
   * Ereditato da {@link OnInit}. Inizializza i dati da mostrare nella tabella delle caratteristiche della macchina.
   */
  ngOnInit(): void {
    this.initTable();
  }

  /**
   * Si interfaccia con un servizio che implementa {@link UpdateDeviceAbstractService} per aggiornare
   * il nome della macchina. Utilizza la {@link FormGroup} per raccogliere il valore dalla e, in caso
   * di errore, la utilizza per visualizzare l'errore.
   */
  updateDeviceName(): void {
    const newName = this.deviceNameControl.value;
    this.updateDeviceService.updateDeviceName(this.device.id, newName)
      .subscribe({
        next: () => {
          this.deviceNameControl.setErrors({duplicateDeviceName: null});
          this.deviceNameControl.updateValueAndValidity();
          if (environment.production) {
            window.location.reload();
          }
        },
        error: (err: { error: StandardError }) => {
          if (err.error.errorCode === 'duplicateDeviceName') {
            this.deviceNameControl.setErrors({duplicateDeviceName: true});
          } else {
            this.notificationService.unexpectedError(`Errore imprevisto: "${JSON.stringify(err)}"`);
          }
        }
      });
  }

  /**
   * Apre la finestra di dialogo di aggiunta di una caratteristica, la classe {@link NewCharacteristicDialogComponent},
   * fornendo le caratteristiche attuali come dati per la validazione.
   * Quando la finestra viene chiusa, tenta l'inserimento interfacciandosi con un servizio che implementa
   * {@link CharacteristicAbstractService}, fornendogli il {@link CharacteristicCreationCommand} restituito dalla
   * finestra di dialogo. Se ha successo, reinizializza la tabella delle caratteristiche.
   */
  openNewCharacteristicDialog(): void {
    const dialogRef = this.matDialog.open(NewCharacteristicDialogComponent, {
      data: {
        characteristics: this.characteristics.data
      }
    });
    dialogRef.afterClosed().subscribe((command: CharacteristicCreationCommand) => {
      if (command) {
        this.characteristicService.addCharacteristic(this.device.id, command)
          .subscribe({
            next: () => {
              this.initTable();
              this.notificationService.notify('Caratteristica aggiunta con successo');
            },
            error: (err: { error: StandardError }) => {
              this.notificationService.unexpectedError(`Errore imprevisto: "${JSON.stringify(err)}"`);
            }
          });
      }
    });
  }

  /**
   * Apre la finestra di dialogo di modifica della caratteristica passata come parametro, la classe
   * {@link UpdateCharacteristicDialogComponent}, inizializzata con i dati della caratteristica e l'id della macchina.
   * Quando la finestra di dialogo viene chiusa, informa questo componente se effettuare o meno l'aggiornamento della
   * tabella.
   *
   * @param characteristic la caratteristica da modificare.
   */
  openUpdateCharacteristicFormDialog(characteristic: Characteristic): void {
    const dialogRef = this.matDialog.open(UpdateCharacteristicDialogComponent, {
      data: {
        deviceId: this.device.id,
        characteristic
      }
    });
    dialogRef.afterClosed().subscribe(reload => {
      if (reload) {
        this.initTable();
      }
    });
  }

  /**
   * Informa l'utente tramite {@link MatSnackBar} che la chiave è stata copiata negli appunti (CTRL-C, o tasto destro -> incolla).
   */
  notifyCopy(): void {
    this.notificationService.notify('Chiave copiata negli appunti');
  }

  /**
   * Utilizza un servizio che implementa {@link CharacteristicAbstractService} per archiviare la caratteristica
   * passata come parametro se non era archiviata, oppure la ripristina se era archiviata. Se la caratteristica
   * sta per essere archiviata viene aperta una finestra di dialogo che chiede conferma dell'operazione.
   *
   * @param characteristic la caratteristica di cui cambiare lo stato di archiviazione.
   */
  toggleCharacteristicStatus(characteristic: Characteristic): void {
    if (characteristic.archived) {
      this.characteristicService.recoverCharacteristic(this.device.id, characteristic.id)
        .subscribe(() => {
          this.initTable();
          this.notificationService.notify('Caratteristica ripristinata con successo');
        });
    } else {
      this.notificationService.requireConfirm(
        `La caratteristica ${characteristic.name} con id ${characteristic.id} sarà archiviata. Continuare?`
      ).subscribe(confirmed => {
        if (confirmed) {
          this.characteristicService.archiveCharacteristic(this.device.id, characteristic.id)
            .subscribe(() => {
              this.initTable();
              this.notificationService.notify('Caratteristica archiviata con successo');
            });
        }
      });
    }
  }

  /**
   * Inizializza i dati della tabella delle caratteristiche, interfacciandosi con un servizio che implementa
   * {@link CharacteristicAbstractService}.
   *
   * @private
   */
  private initTable() {
    this.characteristicService.getCharacteristicsByDevice(this.device.id)
      .subscribe(result => {
        this.characteristics.data = result;
      })
  }

  newNameInvalid() {
    return this.deviceNameControl.invalid || this.deviceNameControl.value === this.device.name;
  }
}
