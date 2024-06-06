export interface LoginCredentials{
  email: string,
  password: string
}


export interface MyToken {
  accessToken: string,
  expiresIn: number
}


export interface UserRegistrationData{
  name: string | null | undefined,
  surname: string | null | undefined,
  email: string | null | undefined,
  password: string | null | undefined
}
