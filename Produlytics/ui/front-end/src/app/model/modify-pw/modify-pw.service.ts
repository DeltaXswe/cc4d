import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ModifyPwAbstractService } from './modify-pw-abstract.service';
import { ModifyPwCommand } from './modify-pw-command';

@Injectable({
  providedIn: 'root'
})
/**
 * Questo service si occupa di effettuare una richiesta HTTP al back-end per
 * effettuare un tentativo di modifica password
 */
export class ModifyPwService implements ModifyPwAbstractService{

  constructor(private http: HttpClient) { }

  /**
   * Effettua una richiesta HTTP PUT al back-end per effettuare la modifica della password
   * @param username Il nome utente
   * @param command Un oggetto contenente la password corrente e quella nuova
   * @returns Un {@link Observable} contenente la risposta del back-end
   */
  modifyPw(username: string, command: ModifyPwCommand){
    return this.http.put(`/accounts/${username}/password`, command);
  }
}
