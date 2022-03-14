import {Type} from "@angular/core";
import {ChartAbstractService} from "../app/model/chart/chart-abstract.service";
import {ListUnarchivedDevicesAbstractService} from "../app/model/public-device/list-unarchived-devices-abstract.service";
import {DeviceAbstractService} from "../app/model/admin-device/device-abstract.service";
import {AccountAbstractService} from "../app/model/admin-account/account-abstract.service";
import {SaveAccountAbstractService} from "../app/model/admin-account/save-account-abstract.service";
import {NewDeviceAbstractService} from "../app/model/admin-device/new-device-abstract.service";

export interface ProdulyticsEnvironment {
  readonly production: boolean
  readonly apiUrl: string
  readonly chartService: Type<ChartAbstractService>
  readonly unarchivedDeviceService: Type<ListUnarchivedDevicesAbstractService>
  readonly deviceService: Type<DeviceAbstractService>
  readonly accountService: Type<AccountAbstractService>
  readonly saveAccountService: Type<SaveAccountAbstractService>
  readonly newDeviceService: Type<NewDeviceAbstractService>
}
