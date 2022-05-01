export interface LoginCommand{
   readonly username: string,
   readonly password: string,
   readonly rememberMe: boolean
}