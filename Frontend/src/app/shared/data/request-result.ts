export class RequestResult {

  public static success(message: string): RequestResult {
    return new RequestResult(false, message);
  }

  public static error(message: string): RequestResult {
    return new RequestResult(true, message);
  }

  private constructor(isError: boolean, message: string) {
    this.isError = isError;
    this.message = message;
  }

  readonly isError: boolean;
  readonly message: string;
}
