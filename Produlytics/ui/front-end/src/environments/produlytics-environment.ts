import {Type} from "@angular/core";
import {ChartAbstractService} from "../app/model/repositories/chart-abstract.service";

export interface ProdulyticsEnvironment {
  production: boolean,
  apiUrl: string,
  chartAbstractService: Type<ChartAbstractService>
}
