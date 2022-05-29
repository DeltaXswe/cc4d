import {Component, OnInit} from '@angular/core';
import {Device} from "../../model/admin-device/device";
import {Router} from "@angular/router";
import {DeviceAbstractService} from "../../model/admin-device/device-abstract.service";
import {map, Observer} from "rxjs";
import {MatDialog} from "@angular/material/dialog";
import {DeviceDataSource} from "./device.data-source";
import {NotificationService} from "../../utils/notification.service";

@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.css']
})
/**
 * Questo component permette di censire e modificare le macchine del sistema.
 */
export class DevicesComponent implements OnInit {
  private reloader: Observer<void> = {
    next: () => {
      this.initTable();
    },
    error: err => {
      this.notificationService.unexpectedError(`Errore inaspettato: ${JSON.stringify(err)}`);
    },
    complete: () => {

    }
  }

  devices = new DeviceDataSource();

  readonly displayedColumns = ['name', 'edit', 'activation', 'status'];

  constructor(
    private router: Router,
    private deviceService: DeviceAbstractService,
    private notificationService: NotificationService
  ) { }

  /**
   * Ereditato dall'interfaccia {@link OnInit}. Inizializza i dati da visualizzare nella tabella delle macchine.
   */
  ngOnInit(): void {
    this.initTable();
  }

  /**
   * Naviga alla pagina di inserimento nuova macchina, tramite un oggetto della classe {@link Router}.
   */
  createDevice(): void {
    this.router.navigate(['gestione-macchine', 'nuova']);
  }

  /**
   * Naviga alla pagina di dettaglio della macchina passata come parametro, tramite un oggetto della classe {@link Router}.
   * @param device la macchina della quale vedere il dettaglio.
   */
  openDeviceDetail(device: Device): void {
    this.router.navigate(['gestione-macchine', device.id]);
  }

  /**
   * Attiva la macchina passata come parametro se era disattivata, altrimenti la disattiva. Questo metodo
   * si interfaccia con il server mediante un servizio che implementa {@link DeviceAbstractService}.
   * @param device il device da attivare o disattivare.
   */
  toggleActivationDevice(device: Device): void {
    if (device.deactivated) {
      this.deviceService.activateDevice(device)
        .pipe(
          map(() => {
            this.notificationService.notify('Macchina attivata con successo.');
          })
        )
        .subscribe(this.reloader);
    } else {
      this.deviceService.deactivateDevice(device)
        .pipe(
          map(() => {
            this.notificationService.notify('Macchina disattivata con successo.');
          })
        )
        .subscribe(this.reloader);
    }
  }

  /**
   * Archivia la macchina passata come parametro se non lo era, viceversa la ripristina. Si
   * interfaccia con un servizio che implementa {@link DeviceAbstractService}.
   * @param device la macchina da archiviare o ripristinare.
   */
  toggleStatusDevice(device: Device): void {
    if (device.archived) {
      this.deviceService.restoreDevice(device)
        .pipe(
          map(() => {
            this.notificationService.notify('Macchina disattivata con successo.');
          })
        )
        .subscribe(this.reloader);
    } else {
      this.notificationService.requireConfirm(`La macchina ${device.name} verrà archiviata.`)
        .subscribe(confirm => {
          if (confirm) {
            this.deviceService.archiveDevice(device)
              .pipe(
                map(() => {
                  this.notificationService.notify('Macchina disattivata con successo.');
                })
              )
              .subscribe(this.reloader);
          }
        });
    }
  }

  /**
   * Inizializza la tabella delle macchine.
   * */
  private initTable() {
    this.deviceService.getDevices()
      .subscribe({
        next: value => {
          this.devices.setData(value);
        },
        error: err => {
          this.notificationService.unexpectedError(`Errore inaspettato: ${JSON.stringify(err)}`);
        }
      });
  }
}
