export interface AccountSaveCommand {
  readonly username: string,
  readonly password: string | null,
  readonly administrator: boolean
}
