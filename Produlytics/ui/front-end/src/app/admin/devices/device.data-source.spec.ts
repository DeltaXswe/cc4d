import {DataSource} from "@angular/cdk/collections";
import {DeviceDataSource} from "./device.data-source";
import {devices, filaioDevice, locomotivaDevice} from "../../test/device/fake-device.service";
import {fakeAsync, tick} from "@angular/core/testing";
import {take} from "rxjs";
import {Device} from "../../model/admin-device/device";

function isSorted(devices: Device[], comparefn: (prev: Device, curr: Device) => boolean) {
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

describe('DeviceDataSource', () => {
  let dataSource: DeviceDataSource;

  beforeEach(() => {
    dataSource = new DeviceDataSource();
  });

  it('visualizza-elenco-macchine', (doneFn) => {
    const devices = [filaioDevice, locomotivaDevice];
    dataSource.setData(devices);
    dataSource.connect().subscribe(value => {
      expect(value).toEqual(devices);
      doneFn();
    });
  });

  it('ordinare-macchine', async () => {
    dataSource.setData(devices);
    dataSource.sortData({
      active: 'name',
      direction: 'asc'
    });
    const data = (await dataSource.connect().pipe(take(1)).toPromise())!;
    expect(isSorted(data, (prev, curr) => prev.name < curr.name)).toBeTrue();
  })
});
