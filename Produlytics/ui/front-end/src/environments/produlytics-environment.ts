import {Type} from "@angular/core";
import {ChartAbstractService} from "../app/model/chart/chart-abstract.service";
import {ListUnarchivedDevicesAbstractService} from "../app/model/public-device/list-unarchived-devices-abstract.service";
import {DeviceAbstractService} from "../app/model/admin-device/device-abstract.service";
import {AccountAbstractService} from "../app/model/admin-account/account-abstract.service";
import {SaveAccountAbstractService} from "../app/model/admin-account/save-account-abstract.service";
import {NewDeviceAbstractService} from "../app/model/admin-device/new/new-device-abstract.service";
import {FindDeviceAbstractService} from "../app/model/admin-device/find-detail/find-device-abstract.service";
import {CharacteristicAbstractService} from "../app/model/admin-device/characteristic/characteristic-abstract.service";
import { LoginAbstractService } from "src/app/model/login/login-abstract.service";
import {UpdateDeviceAbstractService} from "../app/model/admin-device/update/update-device-abstract.service";
import { ModifyPwAbstractService } from "src/app/model/modify-pw/modify-pw-abstract.service";
import {
  UpdateCharacteristicAbstractService
} from "../app/model/admin-device/characteristic/update-characteristic-abstract.service";

export interface ProdulyticsEnvironment {
  readonly production: boolean
  readonly apiUrl: string
  readonly chartService: Type<ChartAbstractService>
  readonly unarchivedDeviceService: Type<ListUnarchivedDevicesAbstractService>
  readonly deviceService: Type<DeviceAbstractService>
  readonly accountService: Type<AccountAbstractService>
  readonly saveAccountService: Type<SaveAccountAbstractService>
  readonly newDeviceService: Type<NewDeviceAbstractService>
  readonly findDeviceService: Type<FindDeviceAbstractService>
  readonly characteristicService: Type<CharacteristicAbstractService>
  readonly loginService: Type<LoginAbstractService>
  readonly updateDeviceService: Type<UpdateDeviceAbstractService>
  readonly updateCharacteristicService: Type<UpdateCharacteristicAbstractService>
  readonly modifyPwService: Type<ModifyPwAbstractService>
}
