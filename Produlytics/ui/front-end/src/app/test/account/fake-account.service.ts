import {Injectable} from "@angular/core";
import {AccountAbstractService} from "../../model/admin-account/account-abstract.service";
import {Observable, of, Subject, throwError} from "rxjs";
import {Account} from "../../model/admin-account/account";
import {SaveAccountAbstractService} from "../../model/admin-account/save-account-abstract.service";
import {AccountSaveCommand} from "../../model/admin-account/account-save-command";
import {LoginAbstractService} from "../../model/login/login-abstract.service";
import {LoginCommand} from "../../model/login/login-command";
import {users} from "./users";
import {HttpErrorResponse, HttpHeaders, HttpParams} from "@angular/common/http";
import {Router} from "@angular/router";
import {MatSnackBar} from "@angular/material/snack-bar";
import {CookieService} from "ngx-cookie-service";
import {AccountEntity} from "./account-entity";
import {ModifyPwAbstractService} from "../../model/modify-pw/modify-pw-abstract.service";
import {ModifyPwCommand} from "../../model/modify-pw/modify-pw-command";

const userNotFoundError = {
  errorCode: 'userNotFound'
};

@Injectable({
  providedIn: 'root'
})
export class FakeAccountService implements
  AccountAbstractService,
  SaveAccountAbstractService,
  LoginAbstractService,
  ModifyPwAbstractService
{

  constructor(
    private matSnackBar: MatSnackBar,
    private cookieService: CookieService,
    private router: Router // qui dobbiamo rivedere la prog.
  ) {
  }

  getAccounts(): Observable<Account[]> {
    return of(users);
  }

  archiveAccount(account: Account): Observable<{}> {
    const source = users.find(source => account.username === source.username);
    if (source) {
      source.archived = true;
      return of({});
    } else {
      return throwError(userNotFoundError);
    }
  }

  recoverAccount(account: Account): Observable<{}> {
    const source = users.find(source => account.username === source.username);
    if (source) {
      source.archived = false;
      return of({});
    } else {
      return throwError(userNotFoundError);
    }
  }

  insertAccount(command: AccountSaveCommand): Observable<{ username: string }> {
    if (!command.password || command.password.length < 6) {
      return throwError({
        errorCode: 'invalidPassword'
      });
    }
    if (users.find(account => account.username === command.username)) {
      return throwError({
        errorCode: 'duplicateUsername'
      });
    } else {
      users.push(AccountEntity.CREATE(command));
      return of(command);
    }
  }

  updateAccount(command: AccountSaveCommand): Observable<{}> {
    const source = users.find(source => command.username === source.username);
    if (source) {
      source.update(command);
      return of({});
    } else {
      return throwError(userNotFoundError);
    }
  }

  login(command: LoginCommand): Observable<any> {
    if(users.find(account => account.username === command.username &&
      users.find(account => account.password === command.password))){
      localStorage.setItem('accessToken', JSON.stringify(
        users.find(wow => wow.username === command.username))
      );
      const httpOptions = {
        headers: new HttpHeaders()
          .set('Authorization', `Basic ${btoa(command.username + ':' + command.password)}`),
        params: new HttpParams()
          .set('remember-me', command.rememberMe)
      };
      console.log(httpOptions);
      if (command.rememberMe)
        this.cookieService.set('PRODULYTICS_RM', 'valore');
      this.router.navigate(['/']);
      return of({});
    } else {
      const error = new HttpErrorResponse({ status: 401 });
      return throwError(() => (error));
    }
  }

  isLogged(): boolean {
    if (localStorage.getItem('accessToken'))
      return true;
    else
      return false;
  }

  isAdmin(): boolean{
    let user = localStorage.getItem('accessToken');
    if (user)
      return JSON.parse(user).administrator;
    else
      return false;
  }

  logout(): Observable<any> {
    localStorage.removeItem('accessToken');
    this.cookieService.delete('PRODULYTICS_RM');
    this.router.navigate(['/login']);
    return of([]);
  }

  getUsername(){
    let user = localStorage.getItem('accessToken');
    if (user)
      return JSON.parse(user).username;
    else
      return 'username';
  }

  modifyPw(username: string, command: ModifyPwCommand){
    if(users.find(account => account.username === username) &&
      users.find(account => account.password === command.currentPassword)){
      users.find(account => account.username === username)!.password = command.newPassword;
      return of({});
    } else{
      const error = new HttpErrorResponse({ status: 401 });
      return throwError(() => error);
    }
  }
}
