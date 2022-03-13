import {Type} from "@angular/core";
import {ChartAbstractService} from "../app/model/chart/chart-abstract.service";
import {ListUnarchivedDevicesAbstractService} from "../app/model/public-device/list-unarchived-devices-abstract.service";

export interface ProdulyticsEnvironment {
  readonly production: boolean
  readonly apiUrl: string
  readonly chartService: Type<ChartAbstractService>
  readonly deviceService: Type<ListUnarchivedDevicesAbstractService>
}
