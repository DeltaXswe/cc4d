import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { ModifyPwService } from './modify-pw.service';

@Injectable()
export abstract class ModifyPwAbstractService {
  
  public abstract modify(username: string, currentPassword: string, newPassword: string): Observable<Object>;

}
