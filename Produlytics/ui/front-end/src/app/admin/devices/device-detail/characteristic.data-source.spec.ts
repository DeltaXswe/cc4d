import {CharacteristicsDataSource} from "./characteristics.data-source";
import {locomotivaDevice} from "../../../test/device/fake-device.service";

describe('CharacteristicsDataSource', () => {
  it('visualizza-elenco-caratteristiche', () => {
    const datasource = new CharacteristicsDataSource();
    datasource.data = locomotivaDevice.characteristics;
    // it seems stupid, ma non lo è! (value accessors)
    expect(datasource.data).toBe(locomotivaDevice.characteristics);
  })
});
