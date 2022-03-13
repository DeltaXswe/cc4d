import {ChartService} from "../app/model/chart/chart-service.service";
import {ProdulyticsEnvironment} from "./produlytics-environment";
import {UnarchivedDeviceService} from "../app/model/public-device/unarchived-device.service";
import {DeviceService} from "../app/model/admin-device/device.service";

export const environment: ProdulyticsEnvironment = {
  production: true,
  apiUrl: '',
  chartService: ChartService,
  unarchivedDeviceService: UnarchivedDeviceService,
  deviceService: DeviceService
};
