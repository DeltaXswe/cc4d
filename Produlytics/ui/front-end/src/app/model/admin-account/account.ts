export interface Account {
  username: string,
  administrator: boolean,
  archived: boolean,
  password: string // TODO hash o non hash questo è il probblema
}
