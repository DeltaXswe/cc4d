import {Account} from "../../model/admin-account/account";
import {AccountSaveCommand} from "../../model/admin-account/account-save-command";

export class AccountEntity {
  username: string;
  administrator: boolean;
  archived: boolean;
  password: string = 'START';

  constructor(account: Account) {
    this.username = account.username;
    this.administrator = account.administrator;
    this.archived = account.archived;
  }

  static CREATE(command: AccountSaveCommand): AccountEntity {
    const nova = new AccountEntity({
      username: command.username,
      archived: false,
      administrator: command.administrator
    });
    nova.password = command.password!;
    return nova;
  }

  update(command: AccountSaveCommand): void {
    this.username = command.username;
    this.administrator = command.administrator;
    if (command.password) { this.password = command.password; }
  }
}
