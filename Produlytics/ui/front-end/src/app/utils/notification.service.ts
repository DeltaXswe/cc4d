import {Injectable} from "@angular/core";
import {MatSnackBar} from "@angular/material/snack-bar";
import {ErrorDialogComponent} from "../components/error-dialog/error-dialog.component";
import {MatDialog} from "@angular/material/dialog";
import {ConfirmDialogComponent} from "../components/confirm-dialog/confirm-dialog.component";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
/**
 * Questa classe raccoglie la configurazione di diversi modi di notificare azioni all'utente.
 * */
export class NotificationService {

  constructor(
    private matSnackBar: MatSnackBar,
    private matDialog: MatDialog
  ) {
  }

  notify(
    message: string,
    action: string = 'Ok'
  ): void {
    this.matSnackBar.open(
      message,
      action,
      {
        duration: 3000
      }
    );
  }

  unexpectedError(message: string): void {
    this.matDialog.open(ErrorDialogComponent, {
      data: {
        message
      }
    });
  }

  requireConfirm(message: string): Observable<boolean> {
    const dialogRef = this.matDialog.open(ConfirmDialogComponent, {
      data: {
        message
      }
    });
    return dialogRef.afterClosed();
  }
}
