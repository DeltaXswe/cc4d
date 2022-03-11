import {ChartService} from "../app/model/chart-service-impl.service";
import {ProdulyticsEnvironment} from "./produlytics-environment";

export const environment: ProdulyticsEnvironment = {
  production: true,
  apiUrl: '',
  chartAbstractService: ChartService
};
