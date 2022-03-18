export interface CharacteristicCreationCommand {
  readonly name: string,
  readonly lowerLimit: number | null,
  readonly upperLimit: number | null,
  readonly autoAdjust: boolean,
  readonly sampleSize: number | null
}
