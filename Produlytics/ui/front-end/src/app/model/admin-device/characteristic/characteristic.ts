export interface Characteristic {
  readonly id: number;
  readonly name: string;
  readonly archived: boolean;
  readonly autoAdjust: boolean;
  readonly lowerLimit: number | null;
  readonly upperLimit: number | null;
  readonly sampleSize: number | null;
}
