import {ChartService} from "../app/model/chart/chart-service.service";
import {ProdulyticsEnvironment} from "./produlytics-environment";
import {UnarchivedDeviceService} from "../app/model/public-device/unarchived-device.service";

export const environment: ProdulyticsEnvironment = {
  production: true,
  apiUrl: '',
  chartService: ChartService,
  deviceService: UnarchivedDeviceService
};
