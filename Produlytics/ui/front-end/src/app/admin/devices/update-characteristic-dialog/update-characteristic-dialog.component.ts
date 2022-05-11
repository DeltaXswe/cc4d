import {Component, Inject, OnInit, ViewChild, ViewEncapsulation} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {Characteristic} from "../../../model/admin-device/characteristic/characteristic";
import {
  UpdateCharacteristicAbstractService
} from "../../../model/admin-device/characteristic/update-characteristic-abstract.service";
import {CharacteristicFormComponent} from "../../../components/characteristic-form/characteristic-form.component";
import {NotificationService} from "../../../utils/notification.service";

@Component({
  selector: 'app-update-characteristic-dialog',
  templateUrl: './update-characteristic-dialog.component.html',
  styleUrls: ['./update-characteristic-dialog.component.css'],
  encapsulation: ViewEncapsulation.None
})
/**
 * Modifica le informazioni di una caratteristica passata come parametro. Richiede un data del tipo:
 *  {
 *     deviceId: number,
 *     characteristic: Characteristic
 *  }.
 */
export class UpdateCharacteristicDialogComponent implements OnInit {
  @ViewChild('charForm') charForm!: CharacteristicFormComponent;

  constructor(
    private updateCharacteristicService: UpdateCharacteristicAbstractService,
    private dialogRef: MatDialogRef<UpdateCharacteristicDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: {
      readonly deviceId: number,
      readonly characteristic: Characteristic,
    },
    private notificationService: NotificationService
  ) {
  }

  ngOnInit(): void {
  }

  /**
   * Annulla l'operazione, chiudendo il {@link MatDialogRef} senza parametri.
   */
  cancel() {
    this.dialogRef.close();
  }

  /**
   * Tenta l'aggiornamento della caratteristica, richiedendo i dati alla {@link CharacteristicFormComponent}.
   * L'aggiornamento avviene interfacciandosi a un servizio che implementa {@link UpdateCharacteristicAbstractService}.
   * In caso di errore viene notificata alla form di mostrare il messaggio d'errore appropriato.
   */
  confirm(): void {
    const rawValue = this.charForm.requireData();
    this.updateCharacteristicService.updateCharacteristic({
      deviceId: this.data.deviceId,
      id: this.data.characteristic.id,
      name: rawValue.name,
      autoAdjust: rawValue.autoAdjust,
      upperLimit: rawValue.upperLimit,
      lowerLimit: rawValue.lowerLimit,
      sampleSize: rawValue.sampleSize
    })
      .subscribe({
        next: () => {
          this.dialogRef.close(true);
        },
        error: err => {
          if (err.errorCode === 'duplicateCharacteristicName') {
            this.charForm.duplicateNameBehavior.next(true);
          } else {
            this.notificationService.unexpectedError(`Errore inaspettato: ${JSON.stringify(err)}`);
          }
        }
      });
  }
}
