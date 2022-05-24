import {Account} from "../../model/admin-account/account";
import {AccountsDataSource} from "./accounts.data-source";
import {aliceUser, bobUser, cosimoUser} from "../../test/account/users";
import {take} from "rxjs";

function isSorted(devices: Account[], comparefn: (prev: Account, curr: Account) => boolean) {
  if (devices.length === 0) { return true; }
  for (let i = 1; i < devices.length; i++) {
    const prev = devices[i - 1];
    const curr = devices[i];
    if (!comparefn(prev, curr)) {
      return false;
    }
  }
  return true;
}

describe('AccountsDataSource', () => {
  let dataSource: AccountsDataSource;

  beforeEach(() => {
    dataSource = new AccountsDataSource();
  });

  it('visualizza-elenco-utenti', (doneFn) => {
    const accounts = [aliceUser, bobUser, cosimoUser];
    dataSource.setData(accounts);
    dataSource.connect().subscribe(value => {
      expect(value).toEqual(accounts);
      doneFn();
    });
  });

  it('ordinare-utenti', async () => {
    const accounts = [aliceUser, bobUser, cosimoUser];
    dataSource.setData(accounts);
    dataSource.sortData({
      active: 'username',
      direction: 'asc'
    });

    const data1 = (await dataSource.connect().pipe(take(1)).toPromise())!;
    expect(isSorted(data1, (prev, curr) => prev.username <= curr.username)).toBeTrue();
    dataSource.sortData({
      active: 'administrator',
      direction: 'asc'
    });
    const data2 = (await dataSource.connect().pipe(take(1)).toPromise())!;
    expect(isSorted(data2, (prev, curr) => prev.administrator <= curr.administrator)).toBeTrue();

  })
});
