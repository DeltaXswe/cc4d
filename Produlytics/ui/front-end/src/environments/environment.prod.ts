import {ChartService} from "../app/model/chart/chart.service";
import {ProdulyticsEnvironment} from "./produlytics-environment";
import {DeviceService} from "../app/model/admin-device/device.service";
import {AccountService} from "../app/model/admin-account/account.service";
import {SaveAccountService} from "../app/model/admin-account/save-account.service";
import {NewDeviceService} from "../app/model/admin-device/new/new-device.service";
import {FindDeviceService} from "../app/model/admin-device/find-detail/find-device.service";
import {CharacteristicService} from "../app/model/admin-device/characteristic/characteristic.service";
import { LoginService } from "src/app/model/login/login.service";
import {UpdateDeviceService} from "../app/model/admin-device/update/update-device.service";
import {UpdateCharacteristicService} from "../app/model/admin-device/characteristic/update-characteristic.service";
import {UnarchivedDeviceService} from "../app/model/device/unarchived-device.service";
import {ModifyPwService} from "../app/model/modify-pw/modify-pw.service";
import {UnarchivedCharacteristicService} from "../app/model/characteristic/unarchived-characteristic.service";

export const environment: ProdulyticsEnvironment = {
  production: true,
  apiUrl: '',
  chartService: ChartService,
  unarchivedDeviceService: UnarchivedDeviceService,
  unarchivedCharacteristicService: UnarchivedCharacteristicService,
  deviceService: DeviceService,
  accountService: AccountService,
  saveAccountService: SaveAccountService,
  newDeviceService: NewDeviceService,
  findDeviceService: FindDeviceService,
  characteristicService: CharacteristicService,
  loginService: LoginService,
  updateDeviceService: UpdateDeviceService,
  updateCharacteristicService: UpdateCharacteristicService,
  modifyPwService: ModifyPwService
};
