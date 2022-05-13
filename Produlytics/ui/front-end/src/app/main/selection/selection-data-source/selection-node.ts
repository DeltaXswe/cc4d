// l'interfaccia con i dati che servono in visualizzazione e selezione
export interface SelectionNode {
  readonly level: number,
  readonly expandable: boolean,
  readonly id: number,
  readonly name: string
}
