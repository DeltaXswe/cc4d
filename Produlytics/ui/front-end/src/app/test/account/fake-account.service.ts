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
import {wrapError} from "../utils";
import {SessionInfo} from "../../model/login/session-info";

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
      return wrapError('userNotFound');
    }
  }

  recoverAccount(account: Account): Observable<{}> {
    const source = users.find(source => account.username === source.username);
    if (source) {
      source.archived = false;
      return of({});
    } else {
      return wrapError('userNotFound');
    }
  }

  insertAccount(command: AccountSaveCommand): Observable<{ username: string }> {
    if (!command.password || command.password.length < 6) {
      return wrapError('invalidPassword');
    }
    if (users.find(account => account.username === command.username)) {
      return wrapError('duplicateUsername');
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
      return wrapError('userNotFound');
    }
  }

  login(command: LoginCommand): Observable<void> {

    if (
      users.find(account => account.username === command.username &&
      users.find(account => account.password === command.password))
    ) {
      const user = users.find(account => account.username === command.username && account.password === command.password)!;
      localStorage.setItem('accessToken', JSON.stringify(
        users.find(wow => wow.username === command.username))
      );
      localStorage.setItem('username', command.username)
      localStorage.setItem('admin', user.administrator ? 'true' : '')
      const httpOptions = {
        headers: new HttpHeaders()
          .set('Authorization', `Basic ${btoa(command.username + ':' + command.password)}`),
        params: new HttpParams()
          .set('remember-me', command.rememberMe)
      };
      console.log(httpOptions);
      if (command.rememberMe)
        this.cookieService.set('PRODULYTICS_RM', 'valore');
      // this.router.navigate(['/']);
      return of();
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
    let user = localStorage.getItem('admin');
    if (user)
      return true;
    else
      return false;
  }

  logout(): Observable<any> {
    localStorage.clear();
    this.cookieService.delete('PRODULYTICS_RM');
    this.router.navigate(['/login']);
    return of([]);
  }

  getUsername(){
    let user = localStorage.getItem('username');
    if (user)
      return user;
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

  autoLogin(): Observable<SessionInfo> {

    const rmCookie = this.cookieService.get('PRODULYTICS_RM');
    console.log(`Just a lil check ${rmCookie} fr rn`);
    const username = localStorage.getItem('username');
    const user = users.find(account => account.username === username);
    if (username && user) {
      localStorage.setItem('accessToken', 'SHAMALAMADINGDONG');
      return of({
        accessToken: 'SHAMALAMADINGDONG',
        username,
        administrator: user.administrator
      });
    } else {
      const error = new HttpErrorResponse({ status: 401 });
      return throwError(() => (error));
    }
  }

  getSessionInfo(): SessionInfo | undefined {
    const username = localStorage.getItem('username');
    const administrator = localStorage.getItem('admin') === 'true';
    if (!username) {
      return undefined;
    } else {
      return {
        username,
        administrator
      }
    }
  }
}
