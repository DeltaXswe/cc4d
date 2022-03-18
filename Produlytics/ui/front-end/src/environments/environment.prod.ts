import {ChartService} from "../app/model/chart/chart-service.service";
import {ProdulyticsEnvironment} from "./produlytics-environment";
import {UnarchivedDeviceService} from "../app/model/public-device/unarchived-device.service";
import {DeviceService} from "../app/model/admin-device/device.service";
import {AccountService} from "../app/model/admin-account/account.service";
import {SaveAccountService} from "../app/model/admin-account/save-account.service";
import {NewDeviceService} from "../app/model/admin-device/new/new-device.service";
import {FindDeviceService} from "../app/model/admin-device/find-detail/find-device.service";
import {CharacteristicService} from "../app/model/admin-device/characteristic/characteristic.service";
import { LoginService } from "src/app/model/login/login.service";
export const environment: ProdulyticsEnvironment = {
  production: true,
  apiUrl: '',
  chartService: ChartService,
  unarchivedDeviceService: UnarchivedDeviceService,
  deviceService: DeviceService,
  accountService: AccountService,
  saveAccountService: SaveAccountService,
  newDeviceService: NewDeviceService,
  findDeviceService: FindDeviceService,
  characteristicService: CharacteristicService
  loginService: loginService
};
