export interface CharacteristicUpdateCommand {
  readonly deviceId: number,
  readonly id: number,
  readonly name: string,
  readonly lowerLimit: number | null,
  readonly upperLimit: number | null,
  readonly autoAdjust: boolean,
  readonly sampleSize: number | null
}
