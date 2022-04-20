import {AccountEntity} from "./account-entity";

export const users: AccountEntity[] = [
  {
    username: 'Gianni',
    administrator: true,
    password: 'Gianni'
  },
  {
    username: 'Cosimo',
    administrator: false,
    password: 'Cosimo'
  },
  {
    username: 'deltax',
    administrator: true,
    password: 'deltax'
  },
  {
    username: 'alice',
    password: 'fossadeileoni',
    administrator: true
  }
].map(AccountEntity.CREATE);
