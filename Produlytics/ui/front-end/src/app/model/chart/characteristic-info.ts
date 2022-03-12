export interface CharacteristicInfo {
  machine: {
    id: number;
    name: string;
  };
  characteristic: {
    code: string;
    name: string;
    machine: number;
    lowerLimit: number;
    upperLimit: number;
    average: number;
  };
}
