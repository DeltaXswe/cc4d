import {ChartService} from "../app/model/chart/chart-service.service";
import {ProdulyticsEnvironment} from "./produlytics-environment";

export const environment: ProdulyticsEnvironment = {
  production: true,
  apiUrl: '',
  chartAbstractService: ChartService
};
