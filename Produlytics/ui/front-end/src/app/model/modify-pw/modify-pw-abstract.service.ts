import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ModifyPwCommand } from './modify-pw-command';

@Injectable()
export abstract class ModifyPwAbstractService {
  
  public abstract modifyPw(username: string, command: ModifyPwCommand): Observable<any>;

}
