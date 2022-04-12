import {JwtData} from "./jwt-data";

export class LoginResult {

  public static success(jwtData: JwtData): LoginResult {
    return new LoginResult(false, null, jwtData);
  }

  public static error(errorMessage: string): LoginResult {
    return new LoginResult(true, errorMessage, null);
  }

  private constructor(isError: boolean, errorMessage: string | null, jwtData: JwtData | null) {
    this.isError = isError;
    this.errorMessage = errorMessage;
    this.jwtData = jwtData;
  }

  readonly isError: boolean;
  readonly errorMessage: string | null;
  readonly jwtData: JwtData | null;
}
