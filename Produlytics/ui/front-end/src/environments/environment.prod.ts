import {ChartService} from "../app/model/chart/chart-service.service";
import {ProdulyticsEnvironment} from "./produlytics-environment";
import {UnarchivedDeviceService} from "../app/model/public-device/unarchived-device.service";
import {DeviceService} from "../app/model/admin-device/device.service";
import {AccountService} from "../app/model/admin-account/account.service";

export const environment: ProdulyticsEnvironment = {
  production: true,
  apiUrl: '',
  chartService: ChartService,
  unarchivedDeviceService: UnarchivedDeviceService,
  deviceService: DeviceService,
  accountService: AccountService
};
