import {Component, OnDestroy, OnInit} from '@angular/core';
import {Device} from "../../model/admin-device/device";
import {Router} from "@angular/router";
import {DeviceAbstractService} from "../../model/admin-device/device-abstract.service";
import {map, Observer, Subject} from "rxjs";
import {MatSnackBar} from "@angular/material/snack-bar";
import {MatDialog} from "@angular/material/dialog";
import {ErrorDialogComponent} from "../../components/error-dialog/error-dialog.component";
import {ConfirmDialogComponent} from "../../components/confirm-dialog/confirm-dialog.component";

@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.css']
})
export class DevicesComponent implements OnInit, OnDestroy {
  private readonly reloader: Observer<void> = {
    next: () => {
      this.deviceService.getDevices()
        .subscribe(value => {
          this.devices = value;
        })
    },
    error: err => {
      this.matDialog.open(ErrorDialogComponent, {
        data: {
          message: err
        }
      })
    },
    complete: () => {

    }
  }

  private unsubscribeAll = new Subject<void>();

  devices: Device[] = [];
  readonly displayedColumns = ['name', 'edit', 'activation', 'status'];

  constructor(
    private router: Router,
    private deviceService: DeviceAbstractService,
    private matSnackBar: MatSnackBar,
    private matDialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.deviceService.getDevices()
      .subscribe(value => {
        this.devices = value;
      });
  }

  ngOnDestroy() {
    this.unsubscribeAll.next();
    this.unsubscribeAll.complete();
  }

  createDevice(): void {

  }

  openDeviceDetail(device: Device): void {
    this.router.navigate(['gestione-macchine', device.id]);
  }

  toggleActivationDevice(device: Device): void {
    if (device.deactivated) {
      this.deviceService.activateDevice(device)
        .pipe(
          map(() => {
            this.matSnackBar.open(
              'Macchina attivata con successo.',
              'Ok'
            );
          })
        )
        .subscribe(this.reloader);
    } else {
      this.deviceService.deactivateDevice(device)
        .pipe(
          map(() => {
            this.matSnackBar.open(
              'Macchina disattivata con successo.',
              'Ok'
            );
          })
        )
        .subscribe(this.reloader);
    }
  }

  toggleStatusDevice(device: Device): void {
    if (device.archived) {
      this.deviceService.restoreDevice(device)
        .pipe(
          map(() => {
            this.matSnackBar.open(
              'Macchina ripristinata con successo.',
              'Ok'
            );
          })
        )
        .subscribe(this.reloader);
    } else {
      const dialogRef = this.matDialog.open(ConfirmDialogComponent, {
        data: {
          message: `La macchina ${device.name} verrà archiviata.`
        }
      });
      dialogRef.afterClosed().subscribe(confirm => {
        if (confirm) {
          this.deviceService.archiveDevice(device)
            .pipe(
              map(() => {
                this.matSnackBar.open(
                  'Macchina archiviata con successo.',
                  'Ok'
                );
              })
            )
            .subscribe(this.reloader);
        }
      });
    }
  }
}
