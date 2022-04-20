// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

import {ProdulyticsEnvironment} from "./produlytics-environment";
import {FakeChartService} from "../app/test/chart/fake-chart.service";
import {FakeDeviceService} from "../app/test/device/fake-device.service";
import {FakeAccountService} from "../app/test/account/fake-account.service";
import {FakeUnarchivedService} from "../app/test/unarchived/fake-unarchived.service";

export const environment: ProdulyticsEnvironment = {
  production: false,
  apiUrl: 'http://localhost/apiweb',
  chartService: FakeChartService,
  unarchivedDeviceService: FakeUnarchivedService,
  unarchivedCharacteristicService: FakeUnarchivedService,
  deviceService: FakeDeviceService,
  accountService: FakeAccountService,
  saveAccountService: FakeAccountService,
  newDeviceService: FakeDeviceService,
  findDeviceService: FakeDeviceService,
  characteristicService: FakeDeviceService,
  loginService: FakeAccountService,
  updateDeviceService: FakeDeviceService,
  modifyPwService: FakeAccountService,
  updateCharacteristicService: FakeDeviceService
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
