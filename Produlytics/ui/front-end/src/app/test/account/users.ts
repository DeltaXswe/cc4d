import {AccountEntity} from "./account-entity";

export const gianniUser = AccountEntity.CREATE({
  username: 'Gianni',
  administrator: true,
  password: 'Gianni'
});

export const cosimoUser = AccountEntity.CREATE(
{
  username: 'Cosimo',
  administrator: false,
  password: 'Cosimo'
});

export const deltaxAdmin = AccountEntity.CREATE({
  username: 'deltax',
  administrator: true,
  password: 'deltax'
});

export const aliceUser = AccountEntity.CREATE({
  username: 'alice',
  password: 'fossadeileoni',
  administrator: true
});

export const bobUser = AccountEntity.CREATE({
  username: 'bob',
  password: 'linkinpark',
  administrator: false
});
bobUser.archived = true;

export const users: AccountEntity[] = [
  gianniUser,
  cosimoUser,
  aliceUser,
  bobUser,
  deltaxAdmin
];
