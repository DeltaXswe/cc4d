import {Type} from "@angular/core";
import {ChartAbstractService} from "../app/model/chart/chart-abstract.service";

export interface ProdulyticsEnvironment {
  readonly production: boolean,
  readonly apiUrl: string,
  readonly chartAbstractService: Type<ChartAbstractService>
}
